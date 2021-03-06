/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.api.java.functions;

import java.util.Iterator;

import eu.stratosphere.api.common.functions.AbstractFunction;
import eu.stratosphere.api.common.functions.GenericGroupReduce;
import eu.stratosphere.util.Collector;


public abstract class ReduceFunction<T> extends AbstractFunction implements GenericGroupReduce<T, T> {
	
	private static final long serialVersionUID = 1L;


	public abstract T reduce(T value1, T value2) throws Exception;
	
	
	@Override
	public final void reduce(Iterator<T> values, Collector<T> out) throws Exception {
		T curr = values.next();
		
		while (values.hasNext()) {
			curr = reduce(curr, values.next());
		}
	}
	
	@Override
	public void combine(Iterator<T> values, Collector<T> out) throws Exception {
		reduce(values, out);
	}
}
