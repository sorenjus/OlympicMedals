
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
        Map<String, int[]> totals = new HashMap<>();
        for (OlympicMedalist o : om) {
            if (totals.isEmpty()) {
                totals.put(o.getCity() + "," + o.getYear() + "," + o.getCountryCode(), new int[3]);
            } else if (o.getCountryCode().equals(totals.keySet())) {
                break;
            } else {
                totals.put(o.getCity() + "," + o.getYear() + "," + o.getCountryCode(), new int[3]);
            }

        }
            for (OlympicMedalist o : om) {
                switch (o.getMedal()) {
                    case "Gold" -> totals.entrySet();/*
                    case "Silver" -> totals.get(o.getCity() + "," + o.getYear() + "," + o.getCountryCode()).set(1, totals.get(o.getCity() + "," + o.getYear() + "," + o.getCountryCode()).get(1) + 1);
                    case "Bronze" -> totals.get(o.getCity() + "," + o.getYear() + "," + o.getCountryCode()).set(2, totals.get(o.getCity() + "," + o.getYear() + "," + o.getCountryCode()).get(2) + 1);
                    */
                }
            }
        int[] medals;
        for(Map.Entry<String, int[]> entry: totals.entrySet()){
            String parts[] = entry.getKey().split(",");
            String countryCode = parts[2];;
            String city = parts[0];
            int year = Integer.parseInt(parts[1]);
            medals = entry.getValue();
        }

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
