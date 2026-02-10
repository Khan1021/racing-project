public class Weather {

    private String condition;       //muddy,dry,icy

    private double speedModifier;
    private double fallRiskModifier;
    private double confidenceModifier;


    public Weather(String type, double speedMod,double fallMod,double conMod){
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


    public static Weather getWeatherCondition(String type){
        switch(type.toLowerCase()){
            case "muddy": return new Weather("Muddy", 0.8, 1.2, -0.1);
            case "dry": return new Weather("Dry",1.2,0.8,+0.1);
            case "icy": return new Weather("icy",0.6,1.5,-0.3);
            default: return new Weather("Normal",1.0,1.0,1.0);
        }//END switch
    }//END getWeatherCondition

}
