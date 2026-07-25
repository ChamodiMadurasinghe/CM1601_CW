package org.example.coursework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RDS {
    private final String fileName;

    public RDS(String fileName){
        this.fileName = fileName;
    }

    public void displayDealers(int count){
        List<String[]> dealers = loadDealers();

        if(dealers.size() < count){
            System.out.println("Not enough dealers in " + fileName + " to select " + count + " unique dealers.");
            return;
        }

        List<String[]> selected = selectRandomDealers(dealers,count);
        sortBYLocation(selected);

        for (String[] dealer : selected){
            System.out.println(dealer[0] + " | " + dealer[1] + " | " + dealer[2] + " | " + dealer[3]);
        }
    }

    private List<String[]> loadDealers(){
        List<String[]> dealers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;

                String[] fields = line.split("\\|", -1);
                if (fields.length <4) continue;

                dealers.add(fields);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return dealers;
    }

    private  List<String[]> selectRandomDealers(List<String[]> allDealers, int count){
        Random random = new Random();
        List<Integer> usedIndexes = new ArrayList<>();
        List<String[]> selected = new ArrayList<>();

        while (selected.size()< count){
            int dealerIndex = random.nextInt(allDealers.size());

            boolean alreadyUsed = false;
            for (int used : usedIndexes){
                if (used == dealerIndex){
                    alreadyUsed = true;
                    break;
                }
            }

            if (!alreadyUsed){
                usedIndexes.add(dealerIndex);
                selected.add(allDealers.get(dealerIndex));
            }
        }
        return selected;
    }

    private  void sortBYLocation(List<String[]> dealers){
        for (int i =1; i<dealers.size(); i++){
            String[] key = dealers.get(i);
            int j = i-1;
            while (j >= 0 && dealers.get(j)[3].trim().compareToIgnoreCase(key[3].trim()) > 0){
                dealers.set(j +1, dealers.get(j));
                j--;
            }
            dealers.set(j+1,key);

        }
    }

}
