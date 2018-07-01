package tech.adrianohrl.stile.control.model.personnel.io;

import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.StringField;
import java.util.ArrayList;
import java.util.List;
import tech.adrianohrl.stile.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class PersonnelReader extends AbstractReader<Employee> {
    
    /** Column Titles **/
    private final static String TYPE_COLUMN_TITLE = PropertyUtil.getPersonnelColumnTitle("Type");
    private final static String CODE_COLUMN_TITLE = PropertyUtil.getPersonnelColumnTitle("Code");
    private final static String NAME_COLUMN_TITLE = PropertyUtil.getPersonnelColumnTitle("Name");
    private final static String LOGIN_COLUMN_TITLE = PropertyUtil.getPersonnelColumnTitle("Login");
    private final static String PASSWORD_COLUMN_TITLE = PropertyUtil.getPersonnelColumnTitle("Password");
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new StringField(TYPE_COLUMN_TITLE, true, getValidTypes()));
        defaultFields.add(new StringField(CODE_COLUMN_TITLE, true));
        defaultFields.add(new StringField(NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(LOGIN_COLUMN_TITLE, false));
        defaultFields.add(new StringField(PASSWORD_COLUMN_TITLE, false));
        return defaultFields;
    }
    
    private List<String> getValidTypes() {
        List<String> validTypes = new ArrayList<>();
        validTypes.add(Subordinate.class.getSimpleName());
        validTypes.add(Supervisor.class.getSimpleName());
        validTypes.add(Manager.class.getSimpleName());
        return validTypes;
    }
    
    @Override
    protected Employee build(List<Field> fields) throws IOException {
        String type = Field.getFieldValue(fields, TYPE_COLUMN_TITLE);
        String name = Field.getFieldValue(fields, NAME_COLUMN_TITLE);
        String code = Field.getFieldValue(fields, CODE_COLUMN_TITLE);
        Employee employee;
        if (type.equals(Subordinate.class.getSimpleName())) {
            employee = new Subordinate(code, name);
        } else {
            String login = Field.getFieldValue(fields, LOGIN_COLUMN_TITLE);
            String password = Field.getFieldValue(fields, PASSWORD_COLUMN_TITLE);
            if (login.isEmpty()) {
                throw new IOException(name + ", as loggable employee, must have a login!!!");
            }
            if (password.isEmpty()) {
                throw new IOException(name + ", as loggable employee, must have a password!!!");
            }
            if (type.equals(Supervisor.class.getSimpleName())) {
                employee = new Supervisor(login, password, code, name);
            } else if (type.equals(Manager.class.getSimpleName())) {
                employee = new Manager(login, password, code, name);
            } else {
                throw new IOException("Invalid employee type!!!");
            }
        }
        return employee;
    }
    
}
