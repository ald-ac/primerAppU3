import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class ConversorDecimal {
 public static void main(String args[]) {
   try {
        ServerSocket sk = new ServerSocket(6666);
        while (true) {
            Socket cliente = sk.accept();
            System.out.println("Pto Rto:"+cliente.getPort()+"\n"+
                             "Pto Loc:"+cliente.getLocalPort()+"\n"+
                             "Sok Loc:"+cliente.getLocalSocketAddress()+"\n"+
                             "Sok Rto:"+cliente.getRemoteSocketAddress()+"\n");
            BufferedReader entrada = new BufferedReader(
                               new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(
                        new OutputStreamWriter(cliente.getOutputStream()),true);
            String datos = entrada.readLine();
            int numeroDec = Integer.parseInt(datos);
            String sSalida = "RESULTADOS \n" +
                            "Binario: " + Integer.toBinaryString(numeroDec) + "\n" +
                            "Octal: " + Integer.toOctalString(numeroDec) + "\n" +
                            "Hexadecimal: " + Integer.toHexString(numeroDec);
            salida.println(sSalida);
            cliente.close();
        }
   } catch (IOException e) {
       System.out.println(e);
   }
 }
}
