import java.util.Scanner;

public class JogoDama {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Tabuleiro_dama tabuleiro = new Tabuleiro_dama();

        System.out.println("Jogo de Damas Iniciado!");
        tabuleiro.visualizar();


        while(tabuleiro.continuarJogo()){
            Pecas pecaAtual;
            if (Tabuleiro_dama.JogadorAtual()){
                pecaAtual = Pecas.PRETO;
            }else pecaAtual = Pecas.BRANCO;

            System.out.println("Jogada da peça: " + pecaAtual);

            int xOrigem, yOrigem, xDestino, yDestino;
            System.out.print("Insira a linha de origem (1-8): ");
            xOrigem = teclado.nextInt() - 1;
            System.out.print("Insira a coluna de origem (a-h) como um número (1-8): ");
            yOrigem = teclado.nextInt() - 1;
            System.out.print("Insira a linha de destino (1-8): ");
            xDestino = teclado.nextInt() - 1;
            System.out.print("Insira a coluna de destino (a-h) como um número (1-8): ");
            yDestino = teclado.nextInt() - 1;

            tabuleiro.fazerJogada(xOrigem, yOrigem, xDestino,yDestino, pecaAtual);
        }
    }
}
