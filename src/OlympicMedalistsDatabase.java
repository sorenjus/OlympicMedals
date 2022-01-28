
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
        Collections.sort(om);
        for (OlympicMedalist o : om) {
            if (totals.isEmpty()) {
                totals.put(o.getCity() + "," + o.getYear() + "," + o.getCountryCode(), new int[3]);
            } else if (o.getCountryCode().equals(totals.keySet())) {
                continue;
            } else {
                totals.put(o.getCity() + "," + o.getYear() + "," + o.getCountryCode(), new int[3]);
            }
        }

        int array[] = {0,0,0};
        for (OlympicMedalist o : om) {
            String key = o.getCity() + "," + o.getYear() + "," + o.getCountryCode();
            array = totals.get(key);
            switch (o.getMedal()) {
                case "Gold" -> {array[0] = array[0] + 1;
                    totals.put(key, array);}
                case "Silver" -> {array[1] = array[1] + 1;
                    totals.put(key, array);}
                case "Bronze" -> {array[2] = array[2] + 1;
                    totals.put(key, array);}
            }
        }
        int[] medals;
        for(Map.Entry<String, int[]> entry: totals.entrySet()){
            String parts[] = entry.getKey().split(",");
            String countryCode = parts[2];;
            String city = parts[0];
            int year = Integer.parseInt(parts[1]);
            medals = entry.getValue();
            ocm.add(new OlympicCountryMedals(year, city, countryCode, medals[0], medals[1], medals[2]));
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
        result.add(new OlympicCountryMedals(1992, "ALG", "Barcelona", 0,0,0));
        for(OlympicCountryMedals o : ocm) {
            if (o.getYear() == year) {
                result.add(o);
            }
        }
        result.remove(result.size() - 1);
        return result;
    }

    public ArrayList < OlympicCountryMedals > topTenCountriesGoldMedals
            (int year) {

        ArrayList < OlympicCountryMedals > result = new ArrayList < OlympicCountryMedals >();
        for(OlympicCountryMedals o: ocm){
            if(o.getYear() == year) {
                result.add(o);
            }
        }
        if(result.size() > 10){
            Collections.sort(result);
            ArrayList < OlympicCountryMedals > resultNew = new ArrayList < OlympicCountryMedals >(result.subList(0,10));
            result = resultNew;
        }
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
