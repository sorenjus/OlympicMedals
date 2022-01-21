
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
public class OlympicMedalistsDatabase
{
    private ArrayList < OlympicMedalist > om;
    private ArrayList < OlympicCountryMedals > ocm;

    public OlympicMedalistsDatabase() {
        om = new ArrayList < OlympicMedalist > ();
        ocm = new ArrayList < OlympicCountryMedals > ();
    }

    public void readOlympicMedalistData(String filename) {
        // Read the full set of data from a text file
        try{
            // open the text file and use a Scanner to read the text
            FileInputStream fileByteStream = new FileInputStream(filename);
            Scanner scnr = new Scanner(fileByteStream);
            scnr.nextLine(); // reads the column headers

            // keep reading as long as there is more data
            while(scnr.hasNext()) {
                // reads each record of the file
                String data = scnr.nextLine();
                OlympicMedalist olympicMedalist = new OlympicMedalist(data);
                om.add(olympicMedalist);
            }
            generateCountryTotalMedals(); 
            fileByteStream.close();
        }
        catch(IOException e) {
            System.out.println("Failed to read the data file: " + filename);
        }
    }

    public void generateCountryTotalMedals() {
        /*int countGold = 0;
        int countSilver = 0;
        int countBronze = 0;

        for(OlympicMedalist o : om){
            if(o.getMedal().equals("Gold")){
                countGold++;
            }
        }*/

        Map<String, ArrayList <Integer>> medalsMap = new HashMap<>();
        for( OlympicMedalist o : om){
            String newKey = o.getCity() + "," + o.getYear() + "," + o.getCountryCode();
            if(medalsMap.isEmpty()){
                medalsMap.put(o.getCountryCode(), new ArrayList<Integer>(Collections.nCopies(3, 0)));
            }
            for(String key : medalsMap.keySet()){
                if (!key.equals(newKey)) {
                    medalsMap.put(o.getCountryCode(), new ArrayList<Integer>(Collections.nCopies(3, 0)));
                }
                switch (o.getMedal()) {
                    case "Gold" -> medalsMap.get(key).set(0, medalsMap.get(key).get(0) + 1);
                    case "Silver" -> medalsMap.get(key).set(1, medalsMap.get(key).get(1) + 1);
                    case "Bronze" -> medalsMap.get(key).set(2, medalsMap.get(key).get(2) + 1);
                }
            }
        }


        System.out.print(medalsMap);
    }

    public int countAllMedalists() {
        return om.size();
    }

    public int countAllWomen() {
        int count = 0;
        for(OlympicMedalist o : om) {
            if (o.getGender().equals("Women")) {
                count++;
            }
        }
        return count;
    }

    public int countAllMen() {
        int count = 0;
        for(OlympicMedalist o : om) {
            if (o.getGender().equals("Men")) {
                count++;
            }
        }
        return count;
    }

    public ArrayList < OlympicMedalist > getMedalistsDatabase() {
        return om;
    }

    public ArrayList < OlympicCountryMedals > getCountryTotalMedalsDatabase() {
        return ocm;
    }

    public ArrayList < OlympicMedalist > searchMedalistsByYear(int year) {
        ArrayList < OlympicMedalist > result = 
            new ArrayList < OlympicMedalist >();
        for(OlympicMedalist o : om) {
            if (o.getYear() == year) {
                result.add(o);
            }
        }
        return result;
    }

    public ArrayList < OlympicMedalist > searchMedalistsByCountry
    (String country) {
        ArrayList < OlympicMedalist > result = 
            new ArrayList < OlympicMedalist >();
        for(OlympicMedalist o : om) {
            if (o.getCountryCode().equals(country)) {
                result.add(o);
            }
        }
        return result;
    }

    public  OlympicMedalist  findAthlete    (String name) {
        OlympicMedalist result = null;
        for(OlympicMedalist o : om) {
            if (o.getName().equalsIgnoreCase(name)) {
                result = o;
            }
        }
        return result;
    }

    public ArrayList < OlympicCountryMedals > searchCountryMedalsByYear(int year) {
        ArrayList < OlympicCountryMedals > result = 
            new ArrayList < OlympicCountryMedals >();
        for(OlympicCountryMedals o : ocm) {
            if (o.getYear() == year) {
                result.add(o);
            }
        }
        return result;
    }

    public ArrayList < OlympicCountryMedals > topTenCountriesGoldMedals
    (int year) {
       
        ArrayList < OlympicCountryMedals > result = 
            new ArrayList < OlympicCountryMedals >();
        // Add your code here
        return result;
    }

    public String findCountryWithHighestBronzeMedalsByYear(int year)
    {
        // One implementation:
        // String result = null;
        // int highestMedalCountSoFar = 0;
        // for (OlympicCountryMedals o : ocm) {
        // if (o.getYear() == year) {
        // if (o.getBronzeMedals() > highestMedalCountSoFar) {
        // result = o.getCountryCode();
        // highestMedalCountSoFar = o.getBronzeMedals();
        // }
        // }
        // }
        // return result;
        // A second implementation:
        ArrayList < OlympicCountryMedals > filteredByYear =          
        ocm.stream().filter(s -> s.getYear() == year).
        collect(Collectors.toCollection(ArrayList::new));
        if (filteredByYear.size() != 0) {
            OlympicCountryMedals r = Collections.max(filteredByYear,
                    Comparator.comparing(o->o.getBronzeMedals()));
            return r.getCountryCode();
        }
        else {
            return null;
        }

    }
}
