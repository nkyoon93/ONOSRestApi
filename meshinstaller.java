/*
 * Copyright 2014 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package default;

import java.awt.List;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.Host;
import org.onosproject.net.host.HostEvent;
import org.onosproject.net.host.HostListener;
import org.onosproject.net.host.HostService;
import org.onosproject.net.intent.HostToHostIntent;
import org.onosproject.net.intent.IntentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Skeletal ONOS application component.
 */
@Component(immediate = true)
public class AppComponent {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected IntentService intentService;
    
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected HostService.hostserviced;
    
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected CoreService coreService;
    
    private InternalHostListner hostlistner = new InternalHostListner();
    
    private List<Host> hosts =coreService Lists.newarrayList();
    private ApplicationId addId;
  
    @Activate
    protected void activate() {
        log.info("Started");
    }

    @Deactivate
    protected void deactivate() {
        log.info("Stopped");
    }
    
    private class InternalHostListner implements HostListener {@Override
    
    	public void event(HostEvent hostEvent) {
    		switch (hostEvent.type()) {
			case HOST_ADDED:
				accConnectivity(hostEvent.subject());
				hosts:add(hostEvent.subject());
				break;
			case HOST_REMOVED:
				break;
				
			case HOST_MOVED:
				break;
				
			case HOST_UPDATED:
				break;
				
			}
   		}
    	
    }
    private void accConnectivity(Host host) {
    	for (Host dst : hosts ) {
    		HostToHostIntent intent = HostToHostIntent.builder().appId(host.id()).two(dst.id()).build();
    		intentService.submit(intent);
    				
    		
    	}
    }

}
