package com.app.quantitymeasurement.model;

public enum Unit {

    // LENGTH (base = METER)
    KILOMETER(MeasurementType.LENGTH, 1000),
    METER(MeasurementType.LENGTH, 1),
    CENTIMETER(MeasurementType.LENGTH, 0.01),
    MILLIMETER(MeasurementType.LENGTH, 0.001),
    MILE(MeasurementType.LENGTH, 1609.34),
    YARD(MeasurementType.LENGTH, 0.9144),
    FEET(MeasurementType.LENGTH, 0.3048),
    INCHES(MeasurementType.LENGTH, 0.0254),

    // WEIGHT (base = KG)
    KILOGRAM(MeasurementType.WEIGHT, 1),
    GRAM(MeasurementType.WEIGHT, 0.001),
    TONNE(MeasurementType.WEIGHT, 1000),
    POUND(MeasurementType.WEIGHT, 0.453592),
    OUNCE(MeasurementType.WEIGHT, 0.0283495),

    // VOLUME (base = LITER)
    LITER(MeasurementType.VOLUME, 1),
    MILLILITER(MeasurementType.VOLUME, 0.001),
    GALLON(MeasurementType.VOLUME, 3.78541),

    // TEMPERATURE (special handling)
    CELSIUS(MeasurementType.TEMPERATURE, 1),
    FAHRENHEIT(MeasurementType.TEMPERATURE, 1),
    KELVIN(MeasurementType.TEMPERATURE, 1);

    private final MeasurementType type;
    private final double factor;

    Unit(MeasurementType type, double factor) {
        this.type = type;
        this.factor = factor;
    }

    public MeasurementType getType() {
        return type;
    }

    public double toBase(double value) {
        if (type == MeasurementType.TEMPERATURE) {
            switch (this) {
                case FAHRENHEIT:
                    return (value - 32) * 5 / 9;
                case KELVIN:
                    return value - 273.15;
                default:
                    return value;
            }
        }
        return value * factor;
    }

    public double fromBase(double baseValue) {
        if (type == MeasurementType.TEMPERATURE) {
            switch (this) {
                case FAHRENHEIT:
                    return (baseValue * 9 / 5) + 32;
                case KELVIN:
                    return baseValue + 273.15;
                default:
                    return baseValue;
            }
        }
        return baseValue / factor;
    }
}
