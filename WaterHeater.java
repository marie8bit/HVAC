/**
 Created by Marie on 10/15/2016.
 In your HVAC program, create a new class called WaterHeater. This represents a service call for a water heater.
 A water heater service call needs an address, date service requested, description of the problem, and the age of
 the water heater. Resolved service calls also need the resolved date, description of the resolution, and the fee
 charged to the customer.

 The city requires that all service calls to water heaters have a mandatory $20 extra charge added. As this applies
 to all water heaters, add a static variable to your class to store this data.

 Add a toString method to WaterHeater which returns a string containing all the static and instance variables for a
 WaterHeater. You should break down the fee into the service charge plus the $20 mandatory city fee.

 */
import java.util.Date;
//class inherits values and methods from superclass
public class WaterHeater extends ServiceCall {
    //unique value for waterheater instance
    private int year;
    //static (doesn't change) variable that applies to all waterheater instances
    static final double cityFee = 20.00;

    public WaterHeater(String serviceAddress, String problemDescription, Date date, Integer year) {
        super(serviceAddress, problemDescription, date);
                this.year=year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public String toString() {

        //These statements use the Java ternary operator
        //This says "if resolvedDate == null, then set resolvedDate to "Unresolved". Otherwise, set resolvedDate to resolvedDate.toString()
        //Same logic as an if-else statment, but more consise if the condition is simple and the if statment's task is
        //to set the value of a variable based on a condition being true or false.
        String resolvedDateString = ( resolvedDate == null) ? "Unresolved" : this.resolvedDate.toString();
        String resolutionString = ( this.resolution == null) ? "Unresolved" : this.resolution;
        String feeString = (fee == UNRESOLVED) ? "Unresolved" : "\nCity Fee $"+ Double.toString(cityFee) +
                "\nService Charge: $"+ Double.toString(fee)+ "\nTotal Fee: $"+Double.toString(fee+cityFee);


        return "Water Heater Unit Service Call " + "\n" +
                "Service Address= " + serviceAddress + "\n" +
                "Problem Description = " + problemDescription  + "\n" +
                "Reported Date = " + reportedDate + "\n" +
                "Resolved Date = " + resolvedDateString + "\n" +
                "Resolution = " + resolutionString + "\n" +
                "Fee = " + feeString ;

    }
}
