package net.anotheria.moskito.webui.embedded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This utility class starts the MoSKitoInspect
 *
 * @author lrosenberg
 * @since 16.04.14 10:58
 */
public class StartMoSKitoInspectBackendForRemote {

	private static Logger log = LoggerFactory.getLogger(StartMoSKitoInspectBackendForRemote.class);

	public static void startMoSKitoInspectBackend() throws MoSKitoInspectStartException{
		Class serverClazz = null;
		Exception exception = null;
		try{
			serverClazz = Class.forName("net.anotheria.moskito.webui.shared.api.generated.CombinedAPIServer");
			Method startMethod = serverClazz.getMethod("createCombinedServicesAndRegisterLocally");
			startMethod.invoke(null);
		}catch(ClassNotFoundException e){
			exception = e;
			log.error("Couldn't find the backend server class", e);
		}catch(NoSuchMethodException e){
			exception = e;
			log.error("Couldn't find the method in server class", e);
		} catch (InvocationTargetException e) {
			exception = e;
			log.error("Couldn't invoke start method", e);
		} catch (IllegalAccessException e) {
			exception = e;
			log.error("Couldn't invoke start method", e);
		}

		if (exception!=null){
			throw new MoSKitoInspectStartException(exception);
		}

	}
}
