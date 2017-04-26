/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import cmp.exceptions.IOException;
import cmp.exceptions.ProductionException;
import cmp.exceptions.ReportException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.Casualty;
import cmp.model.events.EntryEvent;
import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import cmp.model.personal.Supervisor;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import cmp.model.production.ProductionStates;
import cmp.production.control.EntryEventsBuilder;
import cmp.production.reports.EventsPeriodBuilder;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.util.CSVReader;
import cmp.util.CalendarField;
import cmp.util.Calendars;
import cmp.util.DoubleField;
import cmp.util.Field;
import cmp.util.IntegerField;
import cmp.util.StringField;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsReader implements Iterable<EntryEvent> {
    
    /** Column Titles **/
    private final static String DATE_COLUMN_TITLE = "Date";
    private final static String TIME_COLUMN_TITLE = "Time";
    private final static String SUPERVISOR_COLUMN_TITLE = "Supervisor";
    private final static String SECTOR_COLUMN_TITLE = "Sector";
    private final static String SUBORDINATE_COLUMN_TITLE = "Subordinate";
    private final static String MODEL_REFERENCE_COLUMN_TITLE = "Model Reference";
    private final static String PRODUCTION_ORDER_COLUMN_TITLE = "Production Order";
    private final static String PHASE_COLUMN_TITLE = "Phase";
    private final static String PHASE_EXPECTED_DURATION_COLUMN_TITLE = "Phase Expected Duration";
    private final static String PRODUCTION_STATE_COLUMN_TITLE = "Production State";
    private final static String PRODUCED_QUANTITY_COLUMN_TITLE = "Produced Quantity";
    private final static String TOTAL_QUANTITY_COLUMN_TITLE = "Total Quantity";
    private final static String OBSERVATION_COLUMN_TITLE = "Observation";
    private final static String CASUALTY_COLUMN_TITLE = "Casualty";
    
    private final static HashMap<String, Supervisor> supervisors = new HashMap<>();
    private final static HashMap<String, Sector> sectors = new HashMap<>();
    private final static HashMap<String, Subordinate> subordinates = new HashMap<>();
    private final static HashMap<String, Model> models = new HashMap<>();
    private final static HashMap<String, ProductionOrder> productionOrders = new HashMap<>();
    private final static HashMap<String, Phase> phases = new HashMap<>();
    private final static HashMap<String, Casualty> casualties = new HashMap<>();
    private final static ArrayList<PhaseProductionOrder> phaseProductionOrders = new ArrayList<>();
    
    private final CSVReader csvReader;
    private final HashMap<Sector, EntryEventsBuilder> builders = new HashMap<>();
    private final ArrayList<EntryEvent> entryEvents = new ArrayList<>();

    public EntryEventsReader(String fileName) throws IOException {
        csvReader = new CSVReader(fileName, getDefaultFields());   
    }
    
    public void readFile() throws IOException {
        csvReader.readColumnTitles();
        readEntryEvents();
        csvReader.close();
    }
    
    private ArrayList<Field> getDefaultFields() {
        ArrayList<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new CalendarField(DATE_COLUMN_TITLE, "dd/MM/yyyy", true));
        defaultFields.add(new CalendarField(TIME_COLUMN_TITLE, "HH:mm", true));
        defaultFields.add(new StringField(SUPERVISOR_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SECTOR_COLUMN_TITLE, true));
        defaultFields.add(new StringField(SUBORDINATE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(MODEL_REFERENCE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PRODUCTION_ORDER_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PHASE_COLUMN_TITLE, true));
        defaultFields.add(new DoubleField(PHASE_EXPECTED_DURATION_COLUMN_TITLE, true));
        defaultFields.add(new StringField(PRODUCTION_STATE_COLUMN_TITLE, true));
        defaultFields.add(new IntegerField(PRODUCED_QUANTITY_COLUMN_TITLE, false));
        defaultFields.add(new IntegerField(TOTAL_QUANTITY_COLUMN_TITLE, false));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        defaultFields.add(new StringField(CASUALTY_COLUMN_TITLE, false));
        return defaultFields;
    }
    
    private void readEntryEvents() throws IOException {
        ArrayList<Field> fields = csvReader.fillFields();
        while (fields != null) {
            buildEntryEvent(fields);
            fields = csvReader.fillFields();
        }
        for (EntryEventsBuilder builder : builders.values()) {
            for (AbstractEmployeeRelatedEvent entryEvent : builder.getEntryEvents()) {
                entryEvents.add((EntryEvent) entryEvent);
            }
        }
        Collections.sort(entryEvents);
    }
    
    private void buildEntryEvent(ArrayList<Field> fields) throws IOException {
        Calendar calendar = Calendars.sum((Calendar) Field.getFieldValue(fields, DATE_COLUMN_TITLE), (Calendar) Field.getFieldValue(fields, TIME_COLUMN_TITLE));
        Supervisor supervisor = getSupervisor(Field.getFieldValue(fields, SUPERVISOR_COLUMN_TITLE));
        Sector sector = getSector(Field.getFieldValue(fields, SECTOR_COLUMN_TITLE), supervisor);
        Subordinate subordinate = getSubordinate(Field.getFieldValue(fields, SUBORDINATE_COLUMN_TITLE), supervisor);
        Model model = getModel(Field.getFieldValue(fields, MODEL_REFERENCE_COLUMN_TITLE));
        ProductionOrder productionOrder = getProductionOrder(Field.getFieldValue(fields, PRODUCTION_ORDER_COLUMN_TITLE), model);
        Phase phase = getPhase(Field.getFieldValue(fields, PHASE_COLUMN_TITLE), Field.getFieldValue(fields, PHASE_EXPECTED_DURATION_COLUMN_TITLE), model);
        ProductionStates productionState = ProductionStates.valueOf(Field.getFieldValue(fields, PRODUCTION_STATE_COLUMN_TITLE));
        int producedQuantity = Field.getFieldValue(fields, PRODUCED_QUANTITY_COLUMN_TITLE);
        int totalQuantity = Field.getFieldValue(fields, TOTAL_QUANTITY_COLUMN_TITLE);
        String observation = Field.getFieldValue(fields, OBSERVATION_COLUMN_TITLE);
        Casualty casualty = getCasualty(Field.getFieldValue(fields, CASUALTY_COLUMN_TITLE));
        EntryEventsBuilder builder = builders.get(sector);
        try {
            PhaseProductionOrder phaseProductionOrder = getPhaseProductionOrder(phase, productionOrder, totalQuantity);
            switch (productionState) {
                case STARTED:
                    builder.buildEntryEvent(phaseProductionOrder, subordinate, calendar, observation);
                    break;
                case RESTARTED:
                case FINISHED:
                    builder.buildEntryEvent(phaseProductionOrder, subordinate, productionState, calendar, observation);
                    break;
                case PAUSED:
                case RETURNED:
                    if (casualty == null) {
                        throw new IOException("Missing casualty in a PAUSED/RETURNED entry event!!!");
                    }
                    builder.buildEntryEvent(phaseProductionOrder, subordinate, productionState, producedQuantity, calendar, observation, casualty);
                    break;
            }
        } catch (ProductionException e) {
            throw new IOException(e.getMessage());
        }
    }
    
    private Supervisor getSupervisor(String supervisorName) throws IOException {
        if (supervisors.containsKey(supervisorName)) {
            return supervisors.get(supervisorName);
        }
        Supervisor supervisor = createSupervisor(supervisorName);
        supervisors.put(supervisorName, supervisor);
        return supervisor;
    }
    
    protected Supervisor createSupervisor(String supervisorName) throws IOException {
        return new Supervisor("", "", "", supervisorName);
    }
    
    private Sector getSector(String sectorName, Supervisor supervisor) throws IOException  {
        if (sectors.containsKey(sectorName)) {
            return sectors.get(sectorName);
        }
        Sector sector = createSector(sectorName, supervisor);
        builders.put(sector, new EntryEventsBuilder(sector, supervisor));
        sectors.put(sectorName, sector);
        return sector;
    }
    
    protected Sector createSector(String sectorName, Supervisor supervisor) throws IOException  {
        return new Sector(sectorName, supervisor);
    }
    
    private Subordinate getSubordinate(String subordinateName, Supervisor supervisor) throws IOException {
        if (subordinates.containsKey(subordinateName)) {
            return subordinates.get(subordinateName);
        }
        Subordinate subordinate = createSubordinate(subordinateName, supervisor);
        supervisor.getSubordinates().add(subordinate);
        subordinates.put(subordinateName, subordinate);
        return subordinate;
    }
    
    protected Subordinate createSubordinate(String subordinateName, Supervisor supervisor) throws IOException {
        Subordinate subordinate = new Subordinate("", subordinateName);
        supervisor.getSubordinates().add(subordinate);
        return subordinate;
    }
    
    private Model getModel(String modelReference) throws IOException {
        if (models.containsKey(modelReference)) {
            return models.get(modelReference);
        }
        Model model = createModel(modelReference);
        models.put(modelReference, model);
        return model;
    }
    
    protected Model createModel(String modelReference) throws IOException {
        return new Model(modelReference, "");
    }
    
    private ProductionOrder getProductionOrder(String productionOrderName, Model model) throws IOException {
        if (productionOrders.containsKey(productionOrderName)) {
            return productionOrders.get(productionOrderName);
        }
        ProductionOrder productionOrder = createProductionOrder(productionOrderName, model);
        productionOrders.put(productionOrderName, productionOrder);
        return productionOrder;
    }
    
    protected ProductionOrder createProductionOrder(String productionOrderName, Model model) throws IOException {
        return new ProductionOrder(productionOrderName, model);
    }
    
    private Phase getPhase(String phaseName, double expectedDuration, Model model) throws IOException {
        if (phases.containsKey(phaseName)) {
            return phases.get(phaseName);
        }
        Phase phase = createPhase(phaseName, expectedDuration, model);
        phases.put(phaseName, phase);
        return phase;
    }
    
    protected Phase createPhase(String phaseName, double expectedDuration, Model model) throws IOException {
        Phase phase = new Phase(phaseName, expectedDuration);
        model.getPhases().add(phase);
        return phase;
    }
    
    private PhaseProductionOrder getPhaseProductionOrder(Phase phase, ProductionOrder productionOrder, int totalQuantity) throws ProductionException, IOException {
        PhaseProductionOrder phaseProductionOrder = createPhaseProductionOrder(phase, productionOrder, totalQuantity);
        int index = phaseProductionOrders.indexOf(phaseProductionOrder);
        if (index != -1) {
            return phaseProductionOrders.get(index);
        }
        phaseProductionOrders.add(phaseProductionOrder);
        return phaseProductionOrder;        
    }
    
    protected PhaseProductionOrder createPhaseProductionOrder(Phase phase, ProductionOrder productionOrder, int totalQuantity) throws ProductionException, IOException {
        return new PhaseProductionOrder(phase, productionOrder, totalQuantity);
    }
    
    private Casualty getCasualty(String casualtyName) throws IOException {
        if (casualtyName == null || casualtyName.isEmpty()) {
            return null;
        }
        if (casualties.containsKey(casualtyName)) {
            return casualties.get(casualtyName);
        }
        Casualty casualty = createCasualty(casualtyName);
        casualties.put(casualtyName, casualty);
        return casualty;
    }
    
    protected Casualty createCasualty(String casualtyName) throws IOException {
        return new Casualty(casualtyName);
    }
    
    public EmployeeRelatedEventsList getEmployeeRelatedEventsList() {
        return new EmployeeRelatedEventsList(entryEvents);
    }
    
    public EventsPeriodBuilder getEventsPeriodBuilder() throws ReportException {
        return new EventsPeriodBuilder(new EmployeeRelatedEventsList(entryEvents));
    }

    @Override
    public Iterator<EntryEvent> iterator() {
        return entryEvents.iterator();
    }
    
}
