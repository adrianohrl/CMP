package tech.adrianohrl.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public abstract class Field<T extends Comparable<T>> implements Comparable<Field> {
    
    private int columnIndex = -1;
    private final boolean mandatory;
    private final String title;
    private T value = null;
    private List<T> validValues = new ArrayList<>();
    
    protected Field(String title) {
        this(title, false);
    }
    
    protected Field(String title, List<T> validValues) {
        this(title, false);
        this.validValues = validValues;
    }
    
    protected Field(String title, boolean mandatory) {
        this.title = title;
        this.mandatory = mandatory;
    }
    
    protected Field(String title, boolean mandatory, List<T> validValues) {
        this(title, mandatory);
        this.validValues = validValues;
    }

    protected Field(String title, T value) {
        this(title);
        this.value = value;
    }

    protected Field(String title, T value, List<T> validValues) {
        this(title, validValues);
        if (isValid(value))
        {
            this.value = value;
        }
    }

    protected Field(String title, T value, boolean mandatory) {
        this(title, mandatory);
        this.value = value;
    }

    protected Field(String title, T value, boolean mandatory, List<T> validValues) {
        this(title, mandatory);
        this.validValues = validValues;
        if (isValid(value))
        {
            this.value = value;
        }
    }
    
    public boolean exists() {
        return columnIndex != -1;
    }
    
    public boolean isFilled() {
        return value != null;
    }
    
    private boolean isValid(T value) {
        return validValues.isEmpty() || validValues.contains(value);
    }
    
    public static <T> T getFieldValue(List<Field> fields, String fieldTitle) {
        for (Field field : fields) {
            if (field.equals(fieldTitle)) {
                return (T) field.value;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Field && equals((Field) obj) || obj instanceof String && equals((String) obj));
    }
    
    public boolean equals(Field field) {
        return field != null && equals(field.title);
    }
    
    public boolean equals(String field) {
        return this.title.equalsIgnoreCase(field);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "";
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        if (columnIndex < 0) {
            columnIndex = -1;
        }
        this.columnIndex = columnIndex;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public String getTitle() {
        return title;
    }

    public T getValue() {
        return value;
    }
    
    protected void setValue(T value) {
        if (isValid(value))
        {
            this.value = value;
        }
    }
    
    public abstract void setValue(String value) throws ParseException;

    @Override
    public int compareTo(Field field) {
        return columnIndex - field.columnIndex;
    }
    
}
