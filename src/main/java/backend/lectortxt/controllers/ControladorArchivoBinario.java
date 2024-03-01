package backend.lectortxt.controllers;

import backend.Estudiante;
import backend.Identificable;

import java.io.*;

public class ControladorArchivoBinario {

//    public void escribirEstudiante(String nombreArchivo, Estudiante estudiante){
//        try {
//            File file = new File(nombreArchivo);
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(estudiante);
//            objectOutputStream.close();
//            System.out.println("Estudiante agregado " + nombreArchivo);
//        } catch (IOException e) {
//            System.out.println("Archivo no encontrado");
//        }
//    }

    public void escribirObjeto(String nombreArchivo, Object objeto){
        try {
            File file = new File(nombreArchivo);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objeto);
            objectOutputStream.close();
            System.out.println("objeto agregado " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        }
    }

    public void leerEstudiante(String nombreArchivo){
        try {
            File file = new File(nombreArchivo);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Estudiante estudiante = (Estudiante) objectInputStream.readObject();
            System.out.println(estudiante.getIdentificador() + estudiante.getNombre() + estudiante.getCarrera());
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }

    public void leerObjeto(String nombreArchivo){
        try {
            File file = new File(nombreArchivo);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Identificable objeto = (Identificable) objectInputStream.readObject();
            System.out.println(objeto.getIdentificador());
            objectInputStream.close();
        } catch (IOException e) {
            System.out.println("Archivo no encontrado");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }
}
