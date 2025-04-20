public class WeatherCondition {

    private String condition;       //muddy,dry,icy

    private double speedModifier;
    private double fallRiskModifier;
    private double confidenceModifier;


    public WeatherCondition(String type, double speedMod,double fallMod,double conMod){
        this.condition = type;
        this.speedModifier = speedMod;
        this.fallRiskModifier = fallMod;
        this.confidenceModifier = conMod;
    }//END constructor WeatherCondition



    //getter methods
    public double getSpeedModifier() {return speedModifier;}
    public double getFallRiskModifier() {return fallRiskModifier;}
    public double getConfidenceModifier() {return confidenceModifier;}


    public String getType(){
        return condition;
    }//END getType


    public static WeatherCondition getWeatherCondition(String type){
        switch(type.toLowerCase()){
            case "muddy": return new WeatherCondition("Muddy", 0.8, 1.2, -0.1);
            case "dry": return new WeatherCondition("Dry",1.2,0.8,+0.1);
            case "icy": return new WeatherCondition("icy",0.6,1.5,-0.3);
            default: return new WeatherCondition("Normal",1.0,1.0,0.0);
        }//END switch
    }//END getWeatherCondition
}//END WeatherCondition
