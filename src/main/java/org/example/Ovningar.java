package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Ovningar {

    public Ovningar() {
        File folder = new File("files");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    //1. Skapa en fil ”Hello.txt” via FileWriter. Skriv ett meddelande in i den.
    //   Sedan hämta den igen med en FileReader eller Scanner och
    //   skriv ut det som skrevs in.
    public void ovning1() {
        File ovning1File = new File("files/ovning1.txt");
        try {
            FileWriter fileWriter = new FileWriter(ovning1File);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("This is a message.");
            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        try {
            FileReader fileReader = new FileReader(ovning1File);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println(bufferedReader.readLine());
            bufferedReader.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        try {
            Scanner scanner = new Scanner(ovning1File);
            System.out.println("Via scanner: " + scanner.nextLine());
            scanner.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void ovning2() {
        // Gjorde av misstag i ovning1.
    }

    // 3. Skapa en applikation som kollar om en mapp finns. Om mappen inte finns, skapa mappen.
    //Om mappen finns, kolla om en fil med ett särskilt namn finns i den. Om den inte finns, skapa
    //den. Om alla mappar och filer finns, skriv sedan ut filens path samt absolute path.
    public void ovning3() {
        File folder = new File("files/ovning3folder");
        File file = new File("files/ovning3folder/ovning3file.txt");

        if (!folder.exists()) {
            folder.mkdir();
        }
        else if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Path: " + file.getPath());
            System.out.println("Absolute Path: " + file.getAbsolutePath());
        }
    }

    // 4. Tillåt användaren att skriva ned information som skall skrivas i en fil. Sätt dem i en loop där
    //de kan skriva in rad efter rad tills de skriver quit. Låt då alla rader de skrev ned till filen läsas
    //upp till dem igen.
    public void ovning4() {
        File file = new File("files/ovning4.txt");

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please write what to write to file. Write \"_quit_\"");
            while (true) {
                String input = scanner.nextLine().toLowerCase();

                if (input.equals("_quit_")) {
                    break;
                }

                bw.write(input + "\n");
                System.out.println("Wrote: " + input + ". Write more.");
            }
            bw.close();

            System.out.println("You wrote:");
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    // 5. Skapa ett objekt Product med namn, pris och kvantitet. Spara det till en fil i strukturen av en
    //CSV, comma-separated-values. Läs sedan in den igen och återskapa objektet. Du vill nog
    //använda String.split(”,”), och sedan göra om pris och kvantitet till nummer.
    public void ovning5() {
        Product product = new Product("Toy", 25, 10);
        File file = new File("files/ovning5.txt");

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(product.getCSV());
            bw.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            System.out.println("Got string: " + line);
            String[] values = line.split(",");

            String name = values[0];
            int price = Integer.parseInt(values[1]);
            int quantity = Integer.parseInt(values[2]);

            Product recreatedProduct = new Product(name, price, quantity);
            System.out.println("Remade object from file!");
            recreatedProduct.printInfo();
            br.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    // Kan du förlänga förra uppgiften och göra det med en array av products, istället för bara en?
    public void ovning6() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Toy",25,10));
        products.add(new Product("Game",4000,4));
        products.add(new Product("Inner Peace",250000,1));
        products.add(new Product("Chewtoy",2,10));

        File file = new File("files/ovning6.csv");

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < products.size(); i++) {
                bw.write(products.get(i).getCSV());
                if (i < products.size() - 1) {
                    bw.newLine();
                }
            }
            bw.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        ArrayList<Product> recreatedProducts = new ArrayList<>();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                System.out.println("Got string: " + line);
                String[] values = line.split(",");

                String name = values[0];
                int price = Integer.parseInt(values[1]);
                int quantity = Integer.parseInt(values[2]);

                Product recreatedProduct = new Product(name, price, quantity);
                System.out.println("Remade object from file!");
                recreatedProducts.add(recreatedProduct);

                line = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("Got these products!");
        for (Product recreatedProduct: recreatedProducts) {
            recreatedProduct.printInfo();
        }
    }
}
