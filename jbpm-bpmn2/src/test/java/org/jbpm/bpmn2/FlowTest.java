/*
Copyright 2013 JBoss Inc

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package org.jbpm.bpmn2;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.impl.KnowledgeCommandContext;
import org.jbpm.bpmn2.objects.TestWorkItemHandler;
import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.jbpm.process.instance.impl.demo.SystemOutWorkItemHandler;
import org.jbpm.workflow.instance.impl.NodeInstanceImpl;
import org.jbpm.workflow.instance.impl.WorkflowProcessInstanceImpl;
import org.jbpm.workflow.instance.node.CompositeContextNodeInstance;
import org.jbpm.workflow.instance.node.ForEachNodeInstance;
import org.jbpm.workflow.instance.node.ForEachNodeInstance.ForEachJoinNodeInstance;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.kie.api.KieBase;
import org.kie.api.event.process.DefaultProcessEventListener;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.internal.command.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(Parameterized.class)
public class FlowTest extends JbpmBpmn2TestCase {

    @Parameters
    public static Collection<Object[]> persistence() {
        Object[][] data = new Object[][] { { false }, { true } };
        return Arrays.asList(data);
    };

    private static final Logger logger = LoggerFactory.getLogger(FlowTest.class);

    private KieSession ksession;

    public FlowTest(boolean persistence) {
        super(persistence);
    }
    
    @BeforeClass
    public static void setup() throws Exception {
        setUpDataSource();
    }

    @After
    public void dispose() {
        if (ksession != null) {
            ksession.dispose();
            ksession = null;
        }
    }

    @Test
    public void testExclusiveSplit() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-ExclusiveSplit.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", "First");
        params.put("y", "Second");
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testExclusiveSplitXPathAdvanced() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-ExclusiveSplitXPath-advanced.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
        Element hi = doc.createElement("hi");
        Element ho = doc.createElement("ho");
        hi.appendChild(ho);
        Attr attr = doc.createAttribute("value");
        ho.setAttributeNode(attr);
        attr.setValue("a");
        params.put("x", hi);
        params.put("y", "Second");
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testExclusiveSplitXPathAdvanced2() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-ExclusiveSplitXPath-advanced-vars-not-signaled.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
        Element hi = doc.createElement("hi");
        Element ho = doc.createElement("ho");
        hi.appendChild(ho);
        Attr attr = doc.createAttribute("value");
        ho.setAttributeNode(attr);
        attr.setValue("a");
        params.put("x", hi);
        params.put("y", "Second");
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testExclusiveSplitXPathAdvancedWithVars() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-ExclusiveSplitXPath-advanced-with-vars.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
        Element hi = doc.createElement("hi");
        Element ho = doc.createElement("ho");
        hi.appendChild(ho);
        Attr attr = doc.createAttribute("value");
        ho.setAttributeNode(attr);
        attr.setValue("a");
        params.put("x", hi);
        params.put("y", "Second");
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testExclusiveSplitPriority() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-ExclusiveSplitPriority.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", "First");
        params.put("y", "Second");
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testExclusiveSplitDefault() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-ExclusiveSplitDefault.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", "NotFirst");
        params.put("y", "Second");
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testExclusiveXORGateway() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-gatewayTest.bpmn2");
        ksession = createKnowledgeSession(kbase);
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(
                        "<instanceMetadata><user approved=\"false\" /></instanceMetadata>"
                                .getBytes()));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("instanceMetadata", document);
        params.put(
                "startMessage",
                DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse(new ByteArrayInputStream(
                                "<task subject='foobar2'/>".getBytes()))
                        .getFirstChild());
        ProcessInstance processInstance = ksession.startProcess("process",
                params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testInclusiveSplit() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplit.bpmn2");
        ksession = createKnowledgeSession(kbase);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 15);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testInclusiveSplitAndJoin() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoin.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 15);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(2, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinLoop() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinLoop.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 21);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(3, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinLoop2() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinLoop2.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 21);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(3, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinNested() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinNested.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 15);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(2, activeWorkItems.size());
        ksession = restoreSession(ksession, true);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }

        activeWorkItems = workItemHandler.getWorkItems();
        assertEquals(2, activeWorkItems.size());
        ksession = restoreSession(ksession, true);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinEmbedded() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinEmbedded.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 15);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(2, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinWithParallel() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinWithParallel.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 25);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(4, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (WorkItem wi : activeWorkItems) {
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinWithEnd() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinWithEnd.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 25);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(3, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (int i = 0; i < 2; i++) {
            ksession.getWorkItemManager().completeWorkItem(
                    activeWorkItems.get(i).getId(), null);
        }
        assertProcessInstanceActive(processInstance);

        ksession.getWorkItemManager().completeWorkItem(
                activeWorkItems.get(2).getId(), null);
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinWithTimer() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinWithTimer.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 15);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(1, activeWorkItems.size());
        ksession.getWorkItemManager().completeWorkItem(
                activeWorkItems.get(0).getId(), null);
        Thread.sleep(3000);
        assertProcessInstanceActive(processInstance);

        activeWorkItems = workItemHandler.getWorkItems();
        assertEquals(2, activeWorkItems.size());
        ksession.getWorkItemManager().completeWorkItem(
                activeWorkItems.get(0).getId(), null);
        assertProcessInstanceActive(processInstance);

        ksession.getWorkItemManager().completeWorkItem(
                activeWorkItems.get(1).getId(), null);
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitAndJoinExtraPath() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitAndJoinExtraPath.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 25);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);

        ksession.signalEvent("signal", null);

        List<WorkItem> activeWorkItems = workItemHandler.getWorkItems();

        assertEquals(4, activeWorkItems.size());
        ksession = restoreSession(ksession, true);

        for (int i = 0; i < 3; i++) {
            ksession.getWorkItemManager().completeWorkItem(
                    activeWorkItems.get(i).getId(), null);
        }
        assertProcessInstanceActive(processInstance);

        ksession.getWorkItemManager().completeWorkItem(
                activeWorkItems.get(3).getId(), null);
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testInclusiveSplitDefault() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveSplitDefault.bpmn2");
        ksession = createKnowledgeSession(kbase);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", -5);
        ProcessInstance processInstance = ksession.startProcess(
                "com.sample.test", params);
        assertProcessInstanceCompleted(processInstance);

    }
    
    @Test
    public void testInclusiveParallelExclusiveSplitNoLoop() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveNestedInParallelNestedInExclusive.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", new SystemOutWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", new SystemOutWorkItemHandler() {

            @Override
            public void executeWorkItem(WorkItem workItem,  WorkItemManager manager) {
                Integer x = (Integer) workItem.getParameter("input1");
                x++;
                Map<String, Object> results = new HashMap<String, Object>();
                results.put("output1", x);
                manager.completeWorkItem(workItem.getId(), results);
            }
            
        });
        final Map<String, Integer> nodeInstanceExecutionCounter = new HashMap<String, Integer>(); 
        ksession.addEventListener(new DefaultProcessEventListener(){

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {   
                logger.info(event.getNodeInstance().getNodeName());
                Integer value = nodeInstanceExecutionCounter.get(event.getNodeInstance().getNodeName());
                if (value == null) {
                    value = new Integer(0);
                }
                
                value++;
                nodeInstanceExecutionCounter.put(event.getNodeInstance().getNodeName(), value);
            }

            
        });
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 0);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        assertProcessInstanceCompleted(processInstance);
        
        assertEquals(12, nodeInstanceExecutionCounter.size());
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Start"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("XORGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("ANDGateway-diverging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("ORGateway-diverging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI3"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI2"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ORGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Script"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("XORGateway-diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ANDGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI6"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("End"));
    }    
    
    @Test
    public void testInclusiveParallelExclusiveSplitLoop() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveNestedInParallelNestedInExclusive.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", new SystemOutWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", new SystemOutWorkItemHandler() {

            @Override
            public void executeWorkItem(WorkItem workItem,  WorkItemManager manager) {
                Integer x = (Integer) workItem.getParameter("input1");
                x++;
                Map<String, Object> results = new HashMap<String, Object>();
                results.put("output1", x);
                manager.completeWorkItem(workItem.getId(), results);
            }
            
        });
        final Map<String, Integer> nodeInstanceExecutionCounter = new HashMap<String, Integer>(); 
        ksession.addEventListener(new DefaultProcessEventListener(){

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {                
                Integer value = nodeInstanceExecutionCounter.get(event.getNodeInstance().getNodeName());
                if (value == null) {
                    value = new Integer(0);
                }
                
                value++;
                nodeInstanceExecutionCounter.put(event.getNodeInstance().getNodeName(), value);
            }
            
        });
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", -1);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        assertProcessInstanceCompleted(processInstance);
        
        assertEquals(12, nodeInstanceExecutionCounter.size());
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Start"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XORGateway-converging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ANDGateway-diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ORGateway-diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("testWI3"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("testWI2"));
        assertEquals(4, (int)nodeInstanceExecutionCounter.get("ORGateway-converging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("Script"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XORGateway-diverging"));
        assertEquals(4, (int)nodeInstanceExecutionCounter.get("ANDGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI6"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("End"));
    }
    
    @Test
    public void testInclusiveParallelExclusiveSplitNoLoopAsync() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveNestedInParallelNestedInExclusive.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler handler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", handler);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", new SystemOutWorkItemHandler() {

            @Override
            public void executeWorkItem(WorkItem workItem,  WorkItemManager manager) {
                Integer x = (Integer) workItem.getParameter("input1");
                x++;
                Map<String, Object> results = new HashMap<String, Object>();
                results.put("output1", x);
                manager.completeWorkItem(workItem.getId(), results);
            }
            
        });
        final Map<String, Integer> nodeInstanceExecutionCounter = new HashMap<String, Integer>(); 
        ksession.addEventListener(new DefaultProcessEventListener(){

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {                  
                Integer value = nodeInstanceExecutionCounter.get(event.getNodeInstance().getNodeName());
                if (value == null) {
                    value = new Integer(0);
                }
                
                value++;
                nodeInstanceExecutionCounter.put(event.getNodeInstance().getNodeName(), value);
            }

            
        });
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", 0);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        assertProcessInstanceActive(processInstance);
        
        List<WorkItem> workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        // complete work items within OR gateway
        for (WorkItem workItem : workItems) {
            ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
        }
        assertProcessInstanceActive(processInstance);
        
        workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(1, workItems.size());
        // complete last workitem after AND gateway
        for (WorkItem workItem : workItems) {
            ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
        }
        assertProcessInstanceCompleted(processInstance);
        
        assertEquals(12, nodeInstanceExecutionCounter.size());
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Start"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("XORGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("ANDGateway-diverging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("ORGateway-diverging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI3"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI2"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ORGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Script"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("XORGateway-diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ANDGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI6"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("End"));
    } 
    
    @Test
    public void testInclusiveParallelExclusiveSplitLoopAsync() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveNestedInParallelNestedInExclusive.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler handler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", handler);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", new SystemOutWorkItemHandler() {

            @Override
            public void executeWorkItem(WorkItem workItem,  WorkItemManager manager) {
                Integer x = (Integer) workItem.getParameter("input1");
                x++;
                Map<String, Object> results = new HashMap<String, Object>();
                results.put("output1", x);
                manager.completeWorkItem(workItem.getId(), results);
            }
            
        });
        final Map<String, Integer> nodeInstanceExecutionCounter = new HashMap<String, Integer>(); 
        ksession.addEventListener(new DefaultProcessEventListener(){

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) { 
                Integer value = nodeInstanceExecutionCounter.get(event.getNodeInstance().getNodeName());
                if (value == null) {
                    value = new Integer(0);
                }
                
                value++;
                nodeInstanceExecutionCounter.put(event.getNodeInstance().getNodeName(), value);
            }

            
        });
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", -1);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        assertProcessInstanceActive(processInstance);
        
        List<WorkItem> workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        // complete work items within OR gateway
        for (WorkItem workItem : workItems) {
            ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
        }
        assertProcessInstanceActive(processInstance);
        
        workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        // complete work items within OR gateway
        for (WorkItem workItem : workItems) {
            ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
        }
        assertProcessInstanceActive(processInstance);
        
        workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(1, workItems.size());
        // complete last workitem after AND gateway
        for (WorkItem workItem : workItems) {
            ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
        }
        assertProcessInstanceCompleted(processInstance);
        
        assertEquals(12, nodeInstanceExecutionCounter.size());
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Start"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XORGateway-converging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ANDGateway-diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("ORGateway-diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("testWI3"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("testWI2"));
        assertEquals(4, (int)nodeInstanceExecutionCounter.get("ORGateway-converging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("Script"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XORGateway-diverging"));
        assertEquals(4, (int)nodeInstanceExecutionCounter.get("ANDGateway-converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("testWI6"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("End"));
    }
    
    @Test
    public void testInclusiveSplitNested() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveGatewayNested.bpmn2");
        ksession = createKnowledgeSession(kbase);

        TestWorkItemHandler handler = new TestWorkItemHandler();
        TestWorkItemHandler handler2 = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", handler);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", handler2);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", -5);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
        ksession.getWorkItemManager().completeWorkItem(handler2.getWorkItem().getId(), null);
        
        assertProcessInstanceActive(processInstance);
        
        List<WorkItem> workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        
        for (WorkItem wi : workItems) {
            assertProcessInstanceActive(processInstance);
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
        
        assertProcessInstanceCompleted(processInstance);
    }
    
    @Test
    public void testInclusiveSplitWithLoopInside() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveGatewayWithLoopInside.bpmn2");
        ksession = createKnowledgeSession(kbase);
        final Map<String, Integer> nodeInstanceExecutionCounter = new HashMap<String, Integer>(); 
        ksession.addEventListener(new DefaultProcessEventListener(){

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {                 
                logger.info("{} {}", event.getNodeInstance().getNodeName(), ((NodeInstanceImpl) event.getNodeInstance()).getLevel());
                Integer value = nodeInstanceExecutionCounter.get(event.getNodeInstance().getNodeName());
                if (value == null) {
                    value = new Integer(0);
                }
                
                value++;
                nodeInstanceExecutionCounter.put(event.getNodeInstance().getNodeName(), value);
            }

            
        });
        TestWorkItemHandler handler = new TestWorkItemHandler();
        TestWorkItemHandler handler2 = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", handler);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", handler2);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", -1);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        
        assertProcessInstanceActive(processInstance);
        List<WorkItem> workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        
        for (WorkItem wi : workItems) {
            assertProcessInstanceActive(processInstance);
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler2.getWorkItem().getId(), null);
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler2.getWorkItem().getId(), null);
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
        
        assertProcessInstanceCompleted(processInstance);
        assertEquals(10, nodeInstanceExecutionCounter.size());
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Start"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("OR diverging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("tareaWorkflow3"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("tareaWorkflow2"));
        assertEquals(3, (int)nodeInstanceExecutionCounter.get("OR converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("tareaWorkflow6"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("Script"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XOR diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XOR converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("End"));
    }
    
    @Test
    public void testInclusiveSplitWithLoopInsideSubprocess() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-InclusiveGatewayWithLoopInsideSubprocess.bpmn2");
        ksession = createKnowledgeSession(kbase);
        final Map<String, Integer> nodeInstanceExecutionCounter = new HashMap<String, Integer>(); 
        ksession.addEventListener(new DefaultProcessEventListener(){

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {                 
                logger.info("{} {}", event.getNodeInstance().getNodeName(), ((NodeInstanceImpl) event.getNodeInstance()).getLevel());
                Integer value = nodeInstanceExecutionCounter.get(event.getNodeInstance().getNodeName());
                if (value == null) {
                    value = new Integer(0);
                }
                
                value++;
                nodeInstanceExecutionCounter.put(event.getNodeInstance().getNodeName(), value);
            }

            
        });
        TestWorkItemHandler handler = new TestWorkItemHandler();
        TestWorkItemHandler handler2 = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("testWI", handler);
        ksession.getWorkItemManager().registerWorkItemHandler("testWI2", handler2);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("x", -1);
        ProcessInstance processInstance = ksession.startProcess("Process_1", params);
        
        assertProcessInstanceActive(processInstance);
        List<WorkItem> workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        
        for (WorkItem wi : workItems) {
            assertProcessInstanceActive(processInstance);
            ksession.getWorkItemManager().completeWorkItem(wi.getId(), null);
        }
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler2.getWorkItem().getId(), null);
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler2.getWorkItem().getId(), null);
        
        assertProcessInstanceActive(processInstance);
        ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
        
        assertProcessInstanceCompleted(processInstance);
        assertEquals(13, nodeInstanceExecutionCounter.size());
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Start"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("Sub Process 1"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("sb-start"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("sb-end"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("OR diverging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("tareaWorkflow3"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("tareaWorkflow2"));
        assertEquals(3, (int)nodeInstanceExecutionCounter.get("OR converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("tareaWorkflow6"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("Script"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XOR diverging"));
        assertEquals(2, (int)nodeInstanceExecutionCounter.get("XOR converging"));
        assertEquals(1, (int)nodeInstanceExecutionCounter.get("End"));
    }

    @Test
    public void testMultiInstanceLoopCharacteristicsProcessWithORGateway()
            throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-MultiInstanceLoopCharacteristicsProcessWithORgateway.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        Map<String, Object> params = new HashMap<String, Object>();
        List<Integer> myList = new ArrayList<Integer>();
        myList.add(12);
        myList.add(15);
        params.put("list", myList);
        ProcessInstance processInstance = ksession.startProcess(
                "MultiInstanceLoopCharacteristicsProcess", params);

        List<WorkItem> workItems = workItemHandler.getWorkItems();
        assertEquals(4, workItems.size());

        Collection<NodeInstance> nodeInstances = ((WorkflowProcessInstanceImpl) processInstance)
                .getNodeInstances();
        assertEquals(1, nodeInstances.size());
        NodeInstance nodeInstance = nodeInstances.iterator().next();
        assertTrue(nodeInstance instanceof ForEachNodeInstance);

        Collection<NodeInstance> nodeInstancesChild = ((ForEachNodeInstance) nodeInstance)
                .getNodeInstances();
        assertEquals(2, nodeInstancesChild.size());

        for (NodeInstance child : nodeInstancesChild) {
            assertTrue(child instanceof CompositeContextNodeInstance);
            assertEquals(2, ((CompositeContextNodeInstance) child)
                    .getNodeInstances().size());
        }

        ksession.getWorkItemManager().completeWorkItem(
                workItems.get(0).getId(), null);
        ksession.getWorkItemManager().completeWorkItem(
                workItems.get(1).getId(), null);

        processInstance = ksession.getProcessInstance(processInstance.getId());
        nodeInstances = ((WorkflowProcessInstanceImpl) processInstance)
                .getNodeInstances();
        assertEquals(1, nodeInstances.size());
        nodeInstance = nodeInstances.iterator().next();
        assertTrue(nodeInstance instanceof ForEachNodeInstance);

        if (isPersistence()) {
            // when persistence is used there is slightly different behaviour of ContextNodeInstance
            // it's already tested by SimplePersistenceBPMNProcessTest.testMultiInstanceLoopCharacteristicsProcessWithORGateway
            nodeInstancesChild = ((ForEachNodeInstance) nodeInstance)
                    .getNodeInstances();
            assertEquals(1, nodeInstancesChild.size());

            Iterator<NodeInstance> childIterator = nodeInstancesChild
                    .iterator();

            assertTrue(childIterator.next() instanceof CompositeContextNodeInstance);

            ksession.getWorkItemManager().completeWorkItem(
                    workItems.get(2).getId(), null);
            ksession.getWorkItemManager().completeWorkItem(
                    workItems.get(3).getId(), null);

            assertProcessInstanceFinished(processInstance, ksession);
        } else {
            nodeInstancesChild = ((ForEachNodeInstance) nodeInstance)
                    .getNodeInstances();
            assertEquals(2, nodeInstancesChild.size());

            Iterator<NodeInstance> childIterator = nodeInstancesChild
                    .iterator();

            assertTrue(childIterator.next() instanceof CompositeContextNodeInstance);
            assertTrue(childIterator.next() instanceof ForEachJoinNodeInstance);

            ksession.getWorkItemManager().completeWorkItem(
                    workItems.get(2).getId(), null);
            ksession.getWorkItemManager().completeWorkItem(
                    workItems.get(3).getId(), null);

            assertProcessInstanceFinished(processInstance, ksession);
        }

    }

    @Test
    public void testMultiInstanceLoopCharacteristicsProcess() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-MultiInstanceLoopCharacteristicsProcess.bpmn2");
        ksession = createKnowledgeSession(kbase);
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> myList = new ArrayList<String>();
        myList.add("First Item");
        myList.add("Second Item");
        params.put("list", myList);
        ProcessInstance processInstance = ksession.startProcess(
                "MultiInstanceLoopCharacteristicsProcess", params);
        assertProcessInstanceCompleted(processInstance);

    }
    
    @Test
    public void testMultiInstanceLoopCharacteristicsProcess2() throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-MultiInstanceProcessWithOutputOnTask.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler handler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> myList = new ArrayList<String>();
        List<String> myOutList = null;
        myList.add("John");
        myList.add("Mary");
        params.put("miinput", myList);
        
        ProcessInstance processInstance = ksession.startProcess("miprocess", params);
        List<WorkItem> workItems = handler.getWorkItems();
        assertNotNull(workItems);
        assertEquals(2, workItems.size());
        
        myOutList = (List<String>) ksession.execute(new GetProcessVariableCommand(processInstance.getId(), "mioutput"));
        assertNull(myOutList);
        
        
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("reply", "Hello John");
        ksession.getWorkItemManager().completeWorkItem(workItems.get(0).getId(), results);
        
        myOutList = (List<String>) ksession.execute(new GetProcessVariableCommand(processInstance.getId(), "mioutput"));
        assertNull(myOutList);
        
        results = new HashMap<String, Object>();
        results.put("reply", "Hello Mary");
        ksession.getWorkItemManager().completeWorkItem(workItems.get(1).getId(), results);

        myOutList = (List<String>) ksession.execute(new GetProcessVariableCommand(processInstance.getId(), "mioutput"));
        assertNotNull(myOutList);
        assertEquals(2, myOutList.size());
        assertTrue(myOutList.contains("Hello John"));
        assertTrue(myOutList.contains("Hello Mary"));
        
        ksession.getWorkItemManager().completeWorkItem(handler.getWorkItem().getId(), null);
        
        assertProcessInstanceFinished(processInstance, ksession);

    }

    @Test
    public void testMultiInstanceLoopCharacteristicsProcessWithOutput()
            throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-MultiInstanceLoopCharacteristicsProcessWithOutput.bpmn2");
        ksession = createKnowledgeSession(kbase);
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> myList = new ArrayList<String>();
        List<String> myListOut = new ArrayList<String>();
        myList.add("First Item");
        myList.add("Second Item");
        params.put("list", myList);
        params.put("listOut", myListOut);
        assertEquals(0, myListOut.size());
        ProcessInstance processInstance = ksession.startProcess(
                "MultiInstanceLoopCharacteristicsProcessWithOutput", params);
        assertProcessInstanceCompleted(processInstance);
        assertEquals(2, myListOut.size());

    }

    @Test
    public void testMultiInstanceLoopCharacteristicsTaskWithOutput()
            throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-MultiInstanceLoopCharacteristicsTaskWithOutput.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> myList = new ArrayList<String>();
        List<String> myListOut = new ArrayList<String>();
        myList.add("First Item");
        myList.add("Second Item");
        params.put("list", myList);
        params.put("listOut", myListOut);
        assertEquals(0, myListOut.size());
        ProcessInstance processInstance = ksession.startProcess(
                "MultiInstanceLoopCharacteristicsTask", params);
        assertProcessInstanceCompleted(processInstance);
        assertEquals(2, myListOut.size());

    }

    @Test
    public void testMultiInstanceLoopCharacteristicsTask() throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-MultiInstanceLoopCharacteristicsTask.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                new SystemOutWorkItemHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> myList = new ArrayList<String>();
        myList.add("First Item");
        myList.add("Second Item");
        params.put("list", myList);
        ProcessInstance processInstance = ksession.startProcess(
                "MultiInstanceLoopCharacteristicsTask", params);
        assertProcessInstanceCompleted(processInstance);

    }

    @Test
    public void testMultipleInOutgoingSequenceFlows() throws Exception {
        System.setProperty("jbpm.enable.multi.con", "true");
        KieBase kbase = createKnowledgeBase("BPMN2-MultipleInOutgoingSequenceFlows.bpmn2");
        ksession = createKnowledgeSession(kbase);

        final List<Long> list = new ArrayList<Long>();
        ksession.addEventListener(new DefaultProcessEventListener() {
            public void afterProcessStarted(ProcessStartedEvent event) {
                list.add(event.getProcessInstance().getId());
            }
        });

        assertEquals(0, list.size());

        ksession.fireAllRules();
        Thread.sleep(1500);

        assertEquals(1, list.size());
        System.clearProperty("jbpm.enable.multi.con");

    }
    
    @Test
    public void testMultipleEnabledOnSingleConditionalSequenceFlow() throws Exception {
        System.setProperty("jbpm.enable.multi.con", "true");
        KieBase kbase = createKnowledgeBase("BPMN2-MultiConnEnabled.bpmn2");
        ksession = createKnowledgeSession(kbase);

        final List<Long> list = new ArrayList<Long>();
        ksession.addEventListener(new DefaultProcessEventListener() {
            public void afterNodeTriggered(org.kie.api.event.process.ProcessNodeTriggeredEvent event) {
                if ("Task2".equals(event.getNodeInstance().getNodeName())) {
                    list.add(event.getNodeInstance().getNodeId());
                }
            }
        });

        assertEquals(0, list.size());
        ProcessInstance processInstance = ksession.startProcess("BPMN2-MultiConnEnabled");
        assertProcessInstanceActive(processInstance);
        ksession.signalEvent("signal", null, processInstance.getId());
        assertProcessInstanceCompleted(processInstance);

        assertEquals(1, list.size());
        System.clearProperty("jbpm.enable.multi.con");

    }

    /**
     * FIXME testMultipleInOutgoingSequenceFlowsDisable 
     * -- process build is probably caught and there is another exception instead (ArrayIndexOutOfBoundsException)
     */
    @Test
    public void testMultipleInOutgoingSequenceFlowsDisable() throws Exception {
        try {
            KieBase kbase = createKnowledgeBase("BPMN2-MultipleInOutgoingSequenceFlows.bpmn2");
            fail("Should fail as multiple outgoing and incoming connections are disabled by default");
        } catch (Exception e) {
            assertEquals(
                    "This type of node cannot have more than one outgoing connection!",
                    e.getMessage());
        }
    }

    @Test
    public void testConditionalFlow() throws Exception {
        System.setProperty("jbpm.enable.multi.con", "true");
        String processId = "designer.conditional-flow";

        KieBase kbase = createKnowledgeBase("BPMN2-ConditionalFlowWithoutGateway.bpmn2");
        ksession = createKnowledgeSession(kbase);

        WorkflowProcessInstance wpi = (WorkflowProcessInstance) ksession
                .startProcess(processId);

        assertProcessInstanceFinished(wpi, ksession);
        assertNodeTriggered(wpi.getId(), "start", "script", "end1");
        System.clearProperty("jbpm.enable.multi.con");

    }

    @Test
    public void testLane() throws Exception {
        KieBase kbase = createKnowledgeBase("BPMN2-Lane.bpmn2");
        ksession = createKnowledgeSession(kbase);
        TestWorkItemHandler workItemHandler = new TestWorkItemHandler();
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        ProcessInstance processInstance = ksession.startProcess("UserTask");
        assertTrue(processInstance.getState() == ProcessInstance.STATE_ACTIVE);
        ksession = restoreSession(ksession, true);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        WorkItem workItem = workItemHandler.getWorkItem();
        assertNotNull(workItem);
        assertEquals("john", workItem.getParameter("ActorId"));
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("ActorId", "mary");
        ksession.getWorkItemManager().completeWorkItem(workItem.getId(),
                results);
        ksession = restoreSession(ksession, true);
        ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
                workItemHandler);
        workItem = workItemHandler.getWorkItem();
        assertNotNull(workItem);
        assertEquals("mary", workItem.getParameter("ActorId"));
        ksession.getWorkItemManager().completeWorkItem(workItem.getId(), null);
        assertProcessInstanceFinished(processInstance, ksession);
    }
    
    @Test
    public void testExclusiveSplitDefaultNoCondition() throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-ExclusiveSplitDefaultNoCondition.bpmn2");
        KieSession ksession = createKnowledgeSession(kbase);
        ProcessInstance processInstance = ksession.startProcess("com.sample.test");
        assertProcessInstanceFinished(processInstance, ksession);
    }
    
    @Test
    public void testMultipleGatewaysProcess() throws Exception {
        KieBase kbase = createKnowledgeBaseWithoutDumper("BPMN2-MultipleGatewaysProcess.bpmn2");
        ksession = createKnowledgeSession(kbase);
        ksession.addEventListener(new DefaultProcessEventListener(){
            ProcessInstance pi;

            @Override
            public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {                
                if(event.getNodeInstance().getNodeName().equals("CreateAgent")){
                    pi.signalEvent("Signal_1", null);                    
                }                
            }

            @Override
            public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
                logger.info("Before Node triggered event received for node: {}", event.getNodeInstance().getNodeName());
            }

            @Override
            public void beforeProcessStarted(ProcessStartedEvent event) {
                pi=event.getProcessInstance();
                
            }
        });
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "CreateAgent");
        ProcessInstance processInstance = ksession.startProcess("multiplegateways", params);
        
        assertProcessInstanceCompleted(processInstance);
    }

    private static class GetProcessVariableCommand implements GenericCommand<Object> {

        private long processInstanceId;
        private String variableName;
        
        
        public GetProcessVariableCommand(long processInstanceId, String variableName) {
            this.processInstanceId = processInstanceId;
            this.variableName = variableName;
        }


        @Override
        public Object execute(Context context) {
            KieSession ksession = ((KnowledgeCommandContext) context).getKieSession();

            org.jbpm.process.instance.ProcessInstance processInstance = 
                    (org.jbpm.process.instance.ProcessInstance) ksession.getProcessInstance(processInstanceId);

            VariableScopeInstance variableScope = 
                    (VariableScopeInstance) processInstance.getContextInstance(VariableScope.VARIABLE_SCOPE);

            Object variable = variableScope.getVariable(variableName);

            return variable;
        }
        
    }
}
