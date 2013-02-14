package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.guard.BarrierPassGuard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Displays the threshold edit dialog.
 *
 * @author lrosenberg
 * @since 19.10.12 23:26
 */
public class EditThresholdAction extends BaseThresholdsAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {

		String thresholdId = request.getParameter(PARAM_ID);
		Threshold t = ThresholdRepository.getInstance().getById(thresholdId);

		request.setAttribute("target", "Threshold");
		request.setAttribute("definition", t.getDefinition());

		request.setAttribute("thresholdId", t.getId());

		List<ThresholdConditionGuard> guards =  t.getGuards();
		HashMap<ThresholdStatus, String> guardValues = new HashMap<ThresholdStatus, String>();
		for (ThresholdConditionGuard g : guards){
			//we only support barrier guards for now.
			if (g instanceof BarrierPassGuard){
				BarrierPassGuard bpg = (BarrierPassGuard) g;
				guardValues.put(bpg.getTargetStatus(), bpg.getValueAsString());
			}
		}

		for (Map.Entry<ThresholdStatus, String> entry : guardValues.entrySet()){
			request.setAttribute(entry.getKey().name(), entry.getValue());
		}


		return actionMapping.success();
	}
}
