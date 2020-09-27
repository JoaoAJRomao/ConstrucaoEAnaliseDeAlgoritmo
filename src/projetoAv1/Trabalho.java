package ProjetoAv1;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;
public class Trabalho {
public static int Nmeses = 0;
public static int Nfornecedores = 0;
public static void main(String[] args) throws IOException {
String diretorio = System.getProperty("user.dir") + "\\ProjetoUm.txt";
System.out.println(diretorio);
Scanner in = new Scanner(new FileReader(diretorio));
String[] oneLineString = null;
String oneline = in.nextLine();
oneLineString = oneline.split(" ");
Nmeses =  converteInt(oneLineString[0]);
Nfornecedores = converteInt(oneLineString[1]);
int quantLinhas = (((1+Nmeses)*Nmeses)/2)* Nfornecedores; 				  //									    #Tempo					#Vezes
Contrato[] contratos = new Contrato[quantLinhas];  						  //										 C1	  		(((1+m)*m)/2)* n = (m²n + mn)/2
//T(m) = Theta(m²) (Quadrática)
lerArquivo(diretorio,contratos,in);													//								#Tempo					#Vezes
double matriz[][][] = new double[Nfornecedores+1][Nmeses+1][Nmeses+1]; 				//								 C1			(n+1)*(m+1)*(m+1) = (n+1)*(m+1)² =  Nm² + m² + 2nm + 2m + n + 1
//T(m) = Theta(m²) (Quadrática)  

// Das duas estruturas, em termos assintoticos elas apresentam a mesma complexidade Quadrática, porem a matriz apresenta uma utilização maior da memória nesse estudo. Ou seja a matriz ela gera varios vetores de indices que não serão utilizados e simplesmente estão alocando espaço para nada
matriz[0][0][0] = Double.MAX_VALUE;
substituiValoresNaMatriz(contratos, matriz);
//System.out.println("Contrato com o menor valor: " + menorValorContratoV(contratos));
System.out.println("Contrato com o menor valor: " + menorValorContratoM(matriz));
//System.out.println(menorValorContratoIndividualCompleto(matriz,3)); // item D
//System.out.println(menorValorContratoIndividualIF(matriz,2));  // item E
}
private static void substituiValoresNaMatriz(Contrato[] contratos, double[][][] matriz) {
for (Contrato i : contratos) {
matriz[i.getFornecedor()][i.getMesInicio()][i.getMesFim()] = i.getValor();
}
}
private static void lerArquivo(String diretorio,Contrato[] contratos,Scanner in) throws FileNotFoundException {
String[] string = null;
int i = 0;
while (in.hasNextLine()) {
String line = in.nextLine();
string = line.split(" ");
contratos[i] = new Contrato(converteInt(string[0]), converteInt(string[1]), converteInt(string[2]),
converteDouble(string[3]));
i++;
}
}
private static int converteInt(String string) {
return Integer.parseInt(string);
}
private static double converteDouble(String string) {
return Double.parseDouble(string);
}
private static Contrato menorValorContratoV(Contrato[] contratos) {             //                                  #Tempo		#VezesM						#VezesP		
Contrato contrato = contratos[0]; 												//									 C1			  1								1
for (Contrato i : contratos) {  									 			//									 C2			  1				((((1+m)*m)/2)* n) + 1 = (m²n + mn)/2 + 1
if (i.getValor() < contrato.getValor()) 										//									 C3			  0				((((1+m)*m)/2)* n) - 1 = (m²n + mn)/2 + 1
contrato = i;   																//					                 C4			  0				((((1+m)*m)/2)* n) - 1 = (m²n + mn)/2 + 1
} //
return contrato;  																//									 C5           1                             1
}
//TM(m) = Theta(1) (Constante)
//TP(m) = Theta(m²) (Quadrática)

private static String menorValorContratoM(double[][][] matriz) { 				//									#Tempo		#VezesM						#VezesP
double menorValor = matriz[0][0][0];  											//									  C1		  1								1							
int fornecedor = 0, mesInicio = 0, mesFinal = 0; 								//									  C2		  1								1
for (int i = 1;i < matriz.length; i++) {  										//									  C3          1							   n+1
for (int j = 1; j < matriz[i].length; j++) { 									//									  C4		  1							 (m+1)(n)
if(matriz[i][j][j] < menorValor) { 											    //									  C5          1							   m*n
menorValor = matriz[i][j][j]; 													//									  C6		  1							   m*n
fornecedor = i; 																//									  C7		  1							   m*n
mesInicio = j; 																	//									  C8		  1							   m*n
mesFinal = j; 																	//									  C9		  1							   m*n
}
}
}
return "Contrato [fornecedor=" + fornecedor + ", mesInicio=" + mesInicio + ", mesFim=" + mesFinal + ", valor="	//	  C10		  1								1
+ menorValor + "]";
}
//TM(m)  = Theta(1) (Constante)
//TP(m)  = Theta(m) (Linear)

private static String menorValorContratoIndividualCompleto(double[][][]matriz,int m) {	//							#Tempo		#VezesM						#VezesP
double menorValor = matriz[0][0][0];  													//							  C1		  1								1	
int j = 1; 																				//							  C2		  1								1
int k = m;																				//							  C3          1							   	1		
int fornecedor = 0, mesInicio = 0, mesFinal = 0;										//							  C4		  1							 	1
for(int i = 1;i<matriz.length;i++) {   													//							  C5          1							   n+1
if(matriz[i][j][k] < menorValor) {  													//							  C6		  1							    n
menorValor = matriz[i][j][k]; 															//							  C7		  1							    n
fornecedor = i; 																		//							  C8		  1							    n
mesInicio = j;  																		//							  C9		  1							    n
mesFinal = k; 																			//							  C10		  1							    n
}
}
return "Contrato [fornecedor=" + fornecedor + ", mesInicio=" + mesInicio + ", mesFim=" + mesFinal + ", valor="  //    C11		  1								1
+ menorValor + "]";
}
//TM(n) = Theta(1) (Constante)
//TP(n) = Theta(n) (Linear)

private static String menorValorContratoIndividualIF(double[][][]matriz,int m) {		//							#Tempo		#VezesM						#VezesP
double menorValor = matriz[0][0][0];													//							  C1		  1								1	
int j = m;																				//							  C2		  1								1
int k = m;																				//							  C3          1							   	1	
int fornecedor = 0, mesInicio = 0, mesFinal = 0;										//							  C4		  1							 	1
for(int i = 1;i<matriz.length;i++) {													//							  C5          1							   n+1
if(matriz[i][j][k] < menorValor) { 														//							  C6		  1							    n
menorValor = matriz[i][j][k];  															//							  C7		  1							    n
fornecedor = i; 																		//							  C8		  1							    n
mesInicio = j;  																		//							  C9		  1							    n
mesFinal = k; 																			//							  C10		  1							    n
}
}
return "Contrato [fornecedor=" + fornecedor + ", mesInicio=" + mesInicio + ", mesFim=" + mesFinal + ", valor=" //     C11	      1								1
+ menorValor + "]";
}
}
