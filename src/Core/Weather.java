package Core;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class Weather {

    private String temp ="";
    private String icon ="";
    private String rawTemp = "";
    private String rawIcon = "";
    private boolean gotWeather = false;

    private final String xmlUrl = "http://wxdata.weather.com/wxdata/weather/local/UKXX8840?cc=*&unit=m";

    public Weather() throws IOException {
        try{
            InputStream xmlInput = new URL(xmlUrl).openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(xmlInput,"UTF-8"));
            String line;
            while ((line = br.readLine()) != null){
                if(line.contains("<tmp>")){
                    temp = line;
                    String replaceOne = temp.replace("        <tmp>","");
                    rawTemp = replaceOne.replace("</tmp>","");
                    System.out.println(rawTemp);
                }
                if(line.contains("icon") && icon.equals("")){
                    icon = line;
                    String replaceIcon = icon.replace("        <icon>","");
                    rawIcon = replaceIcon.replace("</icon>","");
                    System.out.println(rawIcon);
                }
            }
            gotWeather = true;
            System.out.println("temp :" + temp + " " + " icon#" + icon);
            DocumentBuilderFactory bdf = DocumentBuilderFactory.newInstance();
            bdf.setNamespaceAware(true);
            DocumentBuilder docBuilder = bdf.newDocumentBuilder();
         //   Document doc = docBuilder.parse(new URL(xmlUrl).openStream());
         //   doc.getDocumentElement().normalize();
         //   System.out.println(doc);
         //   NodeList nodeList = (NodeList) doc.getElementById("1");
       //     System.out.println(nodeList.item(1));
       //     System.out.println(nodeList.getLength());

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getIcon() {
        return rawIcon;
    }

    public String getTemp() {
        return rawTemp;
    }

    public boolean gotWeather(){
        return gotWeather;
    }
}
