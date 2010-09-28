/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.java.dev.moskito.core.usecase.running;


/**
 * This is a container for thread local reference to a running thread container.
 * @author lrosenberg
 *
 */
public class RunningUseCaseContainer {
	
	/**
	 * Constant for non existing, non running use case. To prevent memory pollution by NoRunningUseCase instances.
	 */
	public static final RunningUseCase NO_USE_CASE = new NoRunningUseCase();
	/**
	 * Currently running use case.
	 */
	private static ThreadLocal<RunningUseCase> currentUseCase = new ThreadLocal<RunningUseCase>(){
		protected synchronized RunningUseCase initialValue(){
			return NO_USE_CASE;
		}
	};
	
	public static RunningUseCase getCurrentRunningUseCase(){
		return currentUseCase.get();
	}
	
	public static void startUseCase(String name){
		currentUseCase.set(new ExistingRunningUseCase(name));
	}
	
	public static void setCurrentRunningUseCase(RunningUseCase aRunningUseCase){
		currentUseCase.set(aRunningUseCase);
	}
	
	public static RunningUseCase endUseCase(){
		RunningUseCase last = getCurrentRunningUseCase();
		setCurrentRunningUseCase(NO_USE_CASE);
		return last;
	}
	
	/**
	 * Returns true if there is currently a use-case recorded.
	 * @return
	 */
	public static boolean isUseCaseRunning(){
		return !(currentUseCase.get() instanceof NoRunningUseCase);
	}
}
