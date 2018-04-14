/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.production;

import br.com.ceciliaprado.cmp.model.order.ProductionOrder;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Machine;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author adrianohrl
 */
public class ModelOrderTest {
    
    private static Map<String, Subordinate> subordinates;
    private static Map<String, Supervisor> supervisors;
    private static Map<String, Manager> managers;
    private static Map<String, Machine> machines;
    private static Map<String, Sector> sectors;
    private static Map<String, Collection> collections;
    private static Map<String, Variant> variants;
    private static Map<String, Fabric> fabrics;
    private static Map<String, Family> families;
    private static Map<String, Phase> phases;
    private static Map<String, Chart> charts;
    private static Map<String, Model> models;
    
    public static void main(String[] args) {
        createEmployees();
        createMachines();
        createSectors();
        createCollections();
        createVariants();
        createFabrics();
        createFamilies();
        createPhases();
        createCharts();
        createModels();
        Model model = models.get("18137");
        Variant vermelho = variants.get("VERMELHO");
        Variant ocre = variants.get("OCRE");
        Chart pmg = charts.get("PP-P-M-G-GG");
        ChartSize p = pmg.getSize("P");
        ChartSize m = pmg.getSize("M");
        ChartSize g = pmg.getSize("G");
        ProductionOrder order = new ProductionOrder(model.getReference() + "-01", model);
        order.setQuantity(vermelho, p, 4);
        order.setQuantity(vermelho, m, 9);
        order.setQuantity(vermelho, g, 2);
        order.setQuantity(ocre, p, 7);
        order.setQuantity(ocre, g, 6);
        System.out.println("P.O.: " + order);
        String msg = "\n\t";
        for (ChartSize size : order.getModel().getChart()) {
            msg += "\t" + size;
        }
        msg += "\tTotal";
        for (Variant variant : order.getModel().getVariants()) {
            msg += "\n" + variant;
            for (ChartSize size : order.getModel().getChart()) {
                msg += "\t" + order.getQuantity(variant, size);
            }
            msg += "\t" + order.getTotal(variant);
        }
        msg += "\nTotal";
        for (ChartSize size : order.getModel().getChart()) {
            msg += "\t" + order.getTotal(size);
        }
        msg += "\t" + order.getTotal();
        System.out.println(msg);
    }
    
    private static void createEmployees() {
        List<Employee> employees = new ArrayList<>();
        /************************* Subordinates *************************/
        subordinates = new HashMap<>();
        subordinates.put("Joaquina", new Subordinate("00001", "Joaquina"));
        subordinates.put("Joaquim", new Subordinate("00002", "Joaquim"));
        subordinates.put("Rosana", new Subordinate("00003", "Rosana"));
        subordinates.put("Joana", new Subordinate("00004", "Joana"));
        subordinates.put("Maria", new Subordinate("00005", "Maria"));
        subordinates.put("João", new Subordinate("00006", "João"));
        subordinates.put("José", new Subordinate("00007", "José"));
        subordinates.put("Roseli", new Subordinate("00008", "Roseli"));
        subordinates.put("Marcelo", new Subordinate("00009", "Marcelo"));
        subordinates.put("Murilo", new Subordinate("00010", "Murilo"));
        employees.addAll(subordinates.values());
        /************************* Supervisors *************************/
        supervisors = new HashMap<>();
        Supervisor supervisor = new Supervisor("juh", "123456", "00011", "Juliane");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Joaquina"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Rosana"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("José"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Murilo"));
        supervisors.put(supervisor.getName(), supervisor);
        supervisor = new Supervisor("rose", "123", "00012", "Rose");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Joana"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Maria"));
        supervisors.put(supervisor.getName(), supervisor);
        supervisor = new Supervisor("julio", "1234", "00013", "Julio");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Joaquim"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Marcelo"));
        supervisors.put(supervisor.getName(), supervisor);
        supervisor = new Supervisor("ana", "123456789", "00014", "Ana");
        supervisor.getSubordinates().add((Subordinate) subordinates.get("João"));
        supervisor.getSubordinates().add((Subordinate) subordinates.get("Roseli"));
        supervisors.put(supervisor.getName(), supervisor);
        employees.addAll(supervisors.values());
        /************************* Managers *************************/
        managers = new HashMap<>();
        Manager manager = new Manager("carlos", "147258369", "00015", "Carlos");
        manager.getSupervisors().add(supervisors.get("Juliane"));
        manager.getSupervisors().add(supervisors.get("Ana"));
        managers.put(manager.getName(), manager);
        manager = new Manager("rafa", "147258", "00016", "Rafaela");
        manager.getSupervisors().add(supervisors.get("Rose"));
        manager.getSupervisors().add(supervisors.get("Julio"));
        managers.put(manager.getName(), manager);
        employees.addAll(managers.values());
        Collections.sort(employees);
    }
    
    private static void createMachines() {
        machines = new HashMap<>();
        machines.put("Stoll 1", new Machine("Stoll 1"));
        machines.put("Stoll 2", new Machine("Stoll 2"));
        machines.put("Stoll 3", new Machine("Stoll 3"));
        machines.put("Stoll 4", new Machine("Stoll 4"));
        machines.put("Stoll 5", new Machine("Stoll 5"));
        machines.put("Stoll 6", new Machine("Stoll 6"));
        machines.put("Stoll 7", new Machine("Stoll 7"));
        machines.put("Stoll 8", new Machine("Stoll 8"));
        machines.put("Stoll 9", new Machine("Stoll 9"));
        machines.put("Coppo1", new Machine("Coppo 1"));
        machines.put("Coppo2", new Machine("Coppo 2"));
        machines.put("PS 1", new Machine("PS 1"));
        machines.put("PST 1", new Machine("PST 1"));
        machines.put("PST 2", new Machine("PST 2"));
        machines.put("Overloque 1", new Machine("Overloque 1"));
        machines.put("Overloque 2", new Machine("Overloque 2"));
        machines.put("Overloque 3", new Machine("Overloque 3"));
        machines.put("Reta 1", new Machine("Reta 1"));
        machines.put("Reta 2", new Machine("Reta 2"));
        machines.put("Máquina 1", new Machine("Máquina 1"));
        machines.put("Máquina 2", new Machine("Máquina 2"));
        machines.put("Mesa 1", new Machine("Mesa 1"));
        machines.put("Mesa 2", new Machine("Mesa 2"));
        machines.put("Mesa 3", new Machine("Mesa 3"));
    }

    private static void createSectors() {
        sectors = new HashMap<>();
        Sector sector = new Sector("Passadoria", supervisors.get("Ana"));
        sector.getMachines().add(machines.get("Mesa 1"));
        sector.getMachines().add(machines.get("Mesa 2"));
        sector.getMachines().add(machines.get("Mesa 3"));
        sectors.put(sector.getName(), sector);
        sector = new Sector("Costura", supervisors.get("Rose"));
        sector.getMachines().add(machines.get("Overloque 1"));
        sector.getMachines().add(machines.get("Overloque 2"));
        sector.getMachines().add(machines.get("Overloque 3"));
        sector.getMachines().add(machines.get("Reta 1"));
        sector.getMachines().add(machines.get("Reta 2"));
        sectors.put(sector.getName(), sector);
        sector = new Sector("Tecimento", supervisors.get("Julio"));
        sector.getMachines().add(machines.get("Stoll 1"));
        sector.getMachines().add(machines.get("Stoll 2"));
        sector.getMachines().add(machines.get("Stoll 3"));
        sector.getMachines().add(machines.get("Stoll 4"));
        sector.getMachines().add(machines.get("Stoll 5"));
        sector.getMachines().add(machines.get("Stoll 6"));
        sector.getMachines().add(machines.get("Stoll 7"));
        sector.getMachines().add(machines.get("Stoll 8"));
        sector.getMachines().add(machines.get("Stoll 9"));
        sector.getMachines().add(machines.get("Coppo 1"));
        sector.getMachines().add(machines.get("Coppo 2"));
        sector.getMachines().add(machines.get("PS 1"));
        sector.getMachines().add(machines.get("PST 1"));
        sector.getMachines().add(machines.get("PST 2"));
        sectors.put(sector.getName(), sector);
        sector = new Sector("Corte", supervisors.get("Juliane"));
        sector.getMachines().add(machines.get("Máquina 1"));
        sector.getMachines().add(machines.get("Máquina 2"));
        sectors.put(sector.getName(), sector);
    }
    
    private static void createCollections() {
        collections = new HashMap<>();
        collections.put("VER2017", new Collection("VER2017"));
        collections.put("INV2017", new Collection("INV2017"));
        collections.put("VER2018", new Collection("VER2018"));
        collections.put("INV2018", new Collection("INV2018"));
    }

    private static void createVariants() {
        variants = new HashMap<>();
        variants.put("VERMELHO", new Variant("VERMELHO"));
        variants.put("AZUL", new Variant("AZUL"));
        variants.put("OCRE", new Variant("OCRE"));
    }
    
    private static void createFabrics() {
        fabrics = new HashMap<>();
        Fabric fabric = new Fabric("LIBERT", "Uma observacao sobre o tecido Libert", collections.get("INV2018"));
        fabric.getMachines().add(machines.get("Stoll 1"));
        fabric.getMachines().add(machines.get("Stoll 2"));
        fabric.getMachines().add(machines.get("Stoll 4"));
        fabric.getMachines().add(machines.get("Stoll 8"));
        fabric.getVariants().add(variants.get("AZUL"));
        fabric.getVariants().add(variants.get("VERMELHO"));
        fabrics.put(fabric.getName(), fabric);
        fabric = new Fabric("GALÃOZINHO", "Esse galão é pequeno", collections.get("INV2017"));
        fabric.getMachines().add(machines.get("Stoll 1"));
        fabric.getMachines().add(machines.get("Stoll 2"));
        fabric.getMachines().add(machines.get("Stoll 3"));
        fabric.getMachines().add(machines.get("Stoll 4"));
        fabric.getMachines().add(machines.get("Stoll 5"));
        fabric.getMachines().add(machines.get("Stoll 6"));
        fabric.getMachines().add(machines.get("Stoll 7"));
        fabric.getMachines().add(machines.get("Stoll 8"));
        fabric.getMachines().add(machines.get("Coppo 1"));
        fabric.getMachines().add(machines.get("Coppo 2"));
        fabric.getVariants().add(variants.get("AZUL"));
        fabric.getVariants().add(variants.get("VERMELHO"));
        fabrics.put(fabric.getName(), fabric);
        fabric = new Fabric("RENDA", "Esse tecido é uma renda", collections.get("VER2017"));
        fabric.getMachines().add(machines.get("Stoll 1"));
        fabric.getMachines().add(machines.get("Stoll 2"));
        fabric.getMachines().add(machines.get("Stoll 3"));
        fabric.getMachines().add(machines.get("Stoll 4"));
        fabric.getMachines().add(machines.get("Stoll 5"));
        fabric.getMachines().add(machines.get("Stoll 6"));
        fabric.getMachines().add(machines.get("Stoll 7"));
        fabric.getMachines().add(machines.get("Stoll 8"));
        fabric.getVariants().add(variants.get("OCRE"));
        fabric.getVariants().add(variants.get("VERMELHO"));
        fabrics.put(fabric.getName(), fabric);        
    }

    private static void createFamilies() {
        families = new HashMap<>();
        families.put("PALLADIO", new Family("PALLADIO", collections.get("INV2017")));
        families.put("GRANADA", new Family("GRANADA", collections.get("VER2018")));
        families.put("ANTON BLACK", new Family("ANTON BLACK", collections.get("INV2018")));
    }
    
    private static void createPhases() {
        phases = new HashMap<>(); 
        Phase phase = new Phase("Tecimento 1", sectors.get("Tecimento"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Passadoria 1", sectors.get("Passadoria"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Passadoria 2", sectors.get("Passadoria"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Corte 1", sectors.get("Corte"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Corte 2", sectors.get("Corte"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Overloque 1", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Overloque 2", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Overloque 3", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Overloque 4", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Reta 1", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Reta 2", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Remalhadeira 1", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Remalhadeira 2", sectors.get("Costura"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Travete", sectors.get("Arremate"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Crochê", sectors.get("Arremate"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Arremate 1", sectors.get("Arremate"));
        phases.put(phase.getName(), phase);
        phase = new Phase("Arremate 2", sectors.get("Arremate"));
        phases.put(phase.getName(), phase);
    }

    private static void createCharts() {
        charts = new HashMap<>();
        Chart chart = new Chart("PP-P-M-G-GG", "PMG");
        chart.getSizes().add(new ChartSize("PP", 0));
        chart.getSizes().add(new ChartSize("P", 1));
        chart.getSizes().add(new ChartSize("M", 2));
        chart.getSizes().add(new ChartSize("G", 3));
        chart.getSizes().add(new ChartSize("GG", 4));
        charts.put(chart.getName(), chart);
        chart = new Chart("Único", "U");
        chart.getSizes().add(new ChartSize("U", 0));
        charts.put(chart.getName(), chart);
    }

    private static void createModels() {
        models = new HashMap<>();
        Model model = new Model("18137", "VESTIDO LONGO SHARA", families.get("ANTON BLACK"), collections.get("INV2018"), charts.get("PP-P-M-G-GG"));
        model.getParts().add(new Part("Frente", "Frente 123 qdsdawr31", "", fabrics.get("LIBERT")));
        model.getParts().add(new Part("Mangas", "Manga 123 qdsdawr31", "", fabrics.get("RENDA")));
        model.getVariants().add(variants.get("VERMELHO"));
        model.getVariants().add(variants.get("OCRE"));
        models.put(model.getReference(), model);
    }
    
}
