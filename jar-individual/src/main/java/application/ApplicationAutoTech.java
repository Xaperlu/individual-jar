package application;

import java.util.Scanner;
import api.LoocaApi;
import sql.ConexaoAzure;
import sql.ConexaoMySql;
import java.util.Timer;
import java.util.TimerTask;

public class ApplicationAutoTech {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        Boolean loginConfirmado = false;
        ConexaoAzure isExists = new ConexaoAzure();
        ConexaoMySql mysql = new ConexaoMySql();
        String login;
        String senha;
        LoocaApi loocaApi = new LoocaApi();
        
        while(!loginConfirmado){
            System.out.println("-- Aplicação Java AutoTech --"
                + "\n\nInsira o seu Login: ");
            login = leitor.nextLine();
            System.out.println("Insira a sua Senha: ");
            senha = leitor.nextLine();
            loginConfirmado = isExists.verificarLogin(login, senha);
            
            if(loginConfirmado){
                System.out.println("Login Realizado com sucesso!");
            }else{
                System.out.println("Login ou senha inválido");
            }
        }
        
        isExists.getInfoComponentes();
        
        System.out.println("-- Aplicação Java AutoTech --");
        System.out.println("Iniciando capturas de dados...");
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("-- Aplicação Java AutoTech --");
                
                System.out.println(String.format("\nPorcentagem de Uso CPU: %.2f", loocaApi.getCpu()));
                System.out.println(String.format("\nPorcentagem de Uso Memória Ram: %.2f", loocaApi.getMemoria()));
                System.out.println(String.format("\nPorcentagem de Uso Disco: %.2f", loocaApi.getDisco()));
                isExists.setRegistros(loocaApi.getCpu(), loocaApi.getMemoria(), loocaApi.getDisco());
                mysql.setRegistro(loocaApi.getCpu(), loocaApi.getMemoria(), loocaApi.getDisco());     
            }
        }, 0, 5000);
        
    }
}
