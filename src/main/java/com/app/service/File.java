package com.app.service;

import java.io.*;

public class File {
    public void writeScoreInfo(String filename,Main t) throws IOException {
        FileWriter fileOutputStream=new FileWriter(filename);
        fileOutputStream.write(""+ t.getDiametrEarthMil() +" ");
        fileOutputStream.write(""+ t.getDiametrSunMil() +" ");
        fileOutputStream.write(""+ t.obemCircle(t.getDiametrEarthMil())+" ");
        fileOutputStream.write(""+t.obemCircle(t.getDiametrSunMil())+" ");
        fileOutputStream.write(""+t.sunToEarth(t.getDiametrSunMil(), t.getDiametrEarthMil())+" ");
        fileOutputStream.close();

    }
    public void writeTempInfo(String filename,Main t) throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream (filename);
        byte []bytes=new byte[1000];
        bytes= ((t.obemCircle(t.getDiametrEarthMil())+" "+""+ t.obemCircle(t.getDiametrSunMil())+" "+t.sunToEarth(t.getDiametrSunMil(), t.getDiametrEarthMil())+" ").getBytes());
        fileOutputStream.write(bytes);

    }

    public void writeInfile(String filename,Object ob) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(filename);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
        outputStream.writeObject(ob);
    }
    public Object serReadFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fiStream = new FileInputStream(filename);
        ObjectInputStream objectStream = new ObjectInputStream(fiStream);
        Object object = objectStream.readObject();

        fiStream.close();
        objectStream.close();
        return object;
    }
    public void readTempFromFile(String filename) throws IOException, ClassNotFoundException {
        BufferedReader t=new BufferedReader(new FileReader(filename));
        FileInputStream fiStream = new FileInputStream(filename);
        String text=t.readLine();
        String[] qwe=text.split(" ");
        System.out.print("Объем планеты= ");
        System.out.println(qwe[0]);
        System.out.print("Объем звезды= ");
        System.out.println(qwe[1]);
        System.out.print("Отношение звезды к планете= ");
        System.out.println(qwe[2]);
    }
    public void readAllInfo(String filename) throws IOException {
        BufferedReader t=new BufferedReader(new FileReader(filename));
        String text=t.readLine();
        String[] qwe=text.split(" ");
        System.out.print("Диаметр планеты= ");
        System.out.println(qwe[0]);
        System.out.print("Диаметр звезды= ");
        System.out.println(qwe[1]);
        System.out.print("Объем планеты= ");
        System.out.println(qwe[2]);
        System.out.print("Объем звезды= ");
        System.out.println(qwe[3]);
        System.out.print("Отношение звезды к планете= ");
        System.out.println(qwe[4]);
    }
}
