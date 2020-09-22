package testeDeConceito;

public class TestesAleatorios {
	public static void main(String[] args) {
		int[][][] a = {{{1,2,3},{4,5,6},{7,8,9}},{{10,11,12},{13,14,15}}};
		
		for (int[][] is : a) {
			System.out.println();
			for (int[] is2 : is) {//percorre coluna
				System.out.println();
				for (int is3 : is2) {//percorre Linha
					System.out.print(is3+" ");
				}
			}
		}
	}

}
