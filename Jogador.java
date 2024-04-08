import java.util.Scanner;
public class Jogador{
    private Pecas peca;
    private int vitorias;
    private Tabuleiro_dama tabuleiro;
    private Scanner teclado = new Scanner(System.in);

    public Jogador(Pecas peca, Tabuleiro_dama tabuleiro)
    {
        this.peca = peca;
        vitorias = 0;
        this.tabuleiro = tabuleiro;
    }

}
