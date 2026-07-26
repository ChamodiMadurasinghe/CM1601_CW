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

    public void displayDealers(int count) {
        List<Dealer> selected = getRandomDealerSortByLocation(count);
        for (Dealer dealer : selected) {
            System.out.println(dealer);
        }
    }

    public List<Dealer> getRandomDealerSortByLocation(int count) {
        List<Dealer> dealers = loadDealers();

        if (dealers.size() < count) {
            return new ArrayList<>();
        }

        List<Dealer> selected = selectRandomDealers(dealers, count);
        sortBYLocation(selected);
        return selected;
    }

    private List<Dealer> loadDealers(){
        List<Dealer> dealers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))){
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;

                String[] fields = line.split("\\" + "|", -1);
                if (fields.length <4) continue;

                dealers.add(new Dealer(fields[0].trim(),fields[1].trim(),fields[2].trim(),fields[3].trim()));
            }
        } catch (IOException e){
        }

        return dealers;
    }

    private  List<Dealer> selectRandomDealers(List<Dealer> allDealers, int count){
        Random random = new Random();
        List<Integer> usedIndexes = new ArrayList<>();
        List<Dealer> selected = new ArrayList<>();

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

    private  void sortBYLocation(List<Dealer> dealers){
        for (int i =1; i<dealers.size(); i++){
            Dealer key = dealers.get(i);
            int j = i-1;
            while (j >= 0 && dealers.get(j).getLocation().trim().compareToIgnoreCase(key.getLocation().trim()) > 0){
                dealers.set(j +1, dealers.get(j));
                j--;
            }
            dealers.set(j+1,key);

        }
    }

}
