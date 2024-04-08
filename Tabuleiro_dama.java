public class Tabuleiro_dama {
    private final int COLUNAS = 8;
    private final int LINHAS = 8;
    private Pecas[][] marcacao = new Pecas[COLUNAS][LINHAS];
    private static boolean jogadorAtual;// True para preto, false para branco

    public Tabuleiro_dama() {
        inicializar();
        jogadorAtual = true;

    }

    private void inicializar() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                switch (i) {
                    case 0:
                    case 2:
                        if (j % 2 == 1) {
                            marcacao[i][j] = Pecas.PRETO;
                        } else {
                            marcacao[i][j] = Pecas.LIVRE;
                        }
                        break;
                    case 1:
                        if (j % 2 == 0) {
                            marcacao[i][j] = Pecas.PRETO;
                        } else {
                            marcacao[i][j] = Pecas.LIVRE;
                        }
                        break;
                    case 5:
                    case 7:
                        if (j % 2 == 0) {
                            marcacao[i][j] = Pecas.BRANCO;
                        } else {
                            marcacao[i][j] = Pecas.LIVRE;
                        }
                        break;
                    case 6:
                        if (j % 2 == 1) {
                            marcacao[i][j] = Pecas.BRANCO;
                        } else {
                            marcacao[i][j] = Pecas.LIVRE;
                        }
                        break;
                    default:
                        marcacao[i][j] = Pecas.LIVRE;
                        break;
                }
            }
        }
    }


    public void visualizar() {
        System.out.print("\n    ");
        String[] colunas_tabuleiro = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int y = 0; y < COLUNAS; y++) System.out.print(" [" + colunas_tabuleiro[y] + "] ");
        System.out.println();

        for (int x = 0; x < LINHAS; x++) {
            System.out.print("[" + (x + 1) + "] ");
            for (int y = 0; y < COLUNAS; y++) {
                System.out.print("  ");
                switch (marcacao[x][y]) {
                    case LIVRE:
                        System.out.print('_');
                        break;
                    case PRETO:
                        System.out.print('p');
                        break;
                    case BRANCO:
                        System.out.print('b');
                        break;
                }
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    public boolean obrigatorio() {
        Pecas pecaAtual, peca_inimiga;
        if (Tabuleiro_dama.JogadorAtual()) {
            pecaAtual = Pecas.PRETO;
            peca_inimiga = Pecas.BRANCO;
        } else {
            pecaAtual = Pecas.BRANCO;
            peca_inimiga = Pecas.PRETO;
        }
        // cima-esquerda, cima-direita, baixo-esquerda, baixo-direita
        int[][] direcoes = { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
        for (int coluna = 0; coluna < 8; coluna++) {
            for (int linha = 0; linha < 8; linha++) {
                int peca = marcacao[linha][coluna].ordinal();
                if ((pecaAtual == Pecas.PRETO && peca > 0) || (pecaAtual == Pecas.BRANCO && peca < 0)) {
                    for (int[] direcao : direcoes) {
                        int dx = direcao[1];
                        int dy = direcao[0];
                        if (peca == 1 && dy < 0) continue; // Peças comuns do jogador PRETO movem apenas para cima
                        if (peca == -1 && dy > 0) continue; // Peças comuns do jogador BRANCO movem apenas para baixo

                        int destinoColuna = coluna + 2 * dx;
                        int destinoLinha = linha + 2 * dy;
                        if (destinoColuna >= 0 && destinoColuna < 8 && destinoLinha >= 0 && destinoLinha < 8) {
                            int pecaAlvo = marcacao[linha + dy][coluna + dx].ordinal();
                            int destino = marcacao[destinoLinha][destinoColuna].ordinal();
                            if (destino == 0 && ((pecaAlvo < 0 && pecaAtual == Pecas.PRETO) || (pecaAlvo > 0 && pecaAtual == Pecas.BRANCO))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void comer_peca(int xOrigem, int yOrigem, int xDestino, int yDestino, Pecas peca, Pecas peca_inimiga){
            int xCaptura = (xOrigem + xDestino) / 2;
            int yCaptura = (yOrigem + yDestino) / 2;
            if (marcacao[yCaptura][xCaptura] == peca_inimiga && marcacao[yDestino][xDestino] == Pecas.LIVRE) {
                marcacao[yOrigem][xOrigem] = Pecas.LIVRE;
                marcacao[yCaptura][xCaptura] = Pecas.LIVRE;
                marcacao[yDestino][xDestino] = peca;
                System.out.println("Uma peça " + peca_inimiga + " foi capturada!");
            }
    }

    public boolean continuarJogo(){
        int n_pretas = 0, n_brancas = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (marcacao[i][j] == Pecas.PRETO){
                    n_pretas += 1;
                }
                if (marcacao[i][j] == Pecas.BRANCO){
                    n_pretas += 1;
                }
            }
        }
        return n_brancas != 0 || n_pretas != 0;
    }

    public void fazerJogada(int xOrigem, int yOrigem, int xDestino, int yDestino, Pecas peca) {
        if (xOrigem < 0 || xOrigem >= COLUNAS || yOrigem < 0 || yOrigem >= LINHAS ||
                xDestino < 0 || xDestino >= COLUNAS || yDestino < 0 || yDestino >= LINHAS) {
            System.out.println("Jogada inválida. Posições fora dos limites do tabuleiro.");
        } else {
            Pecas peca_inimiga;
            if (peca == Pecas.BRANCO) {
                peca_inimiga = Pecas.PRETO;
            } else {
                peca_inimiga = Pecas.BRANCO;
            }

            if (obrigatorio()){
                if (Math.abs(xOrigem - xDestino) == 2 && Math.abs(yOrigem - yDestino) == 2) {
                    comer_peca(xOrigem, yOrigem, xDestino, yDestino, peca, peca_inimiga);
                    System.out.println("Jogada obrigatória realizada");
                    System.out.println("Deu certo porra");
                    visualizar();
                    jogadorAtual = !jogadorAtual;
                    return;
                }
            }

            // Verifica se possui 2 de diferença com o destino, se tiver e possuir uma peça no x,y de captura, ele fa a jogada
            if (Math.abs(xOrigem - xDestino) == 2 && Math.abs(yOrigem - yDestino) == 2) {
                comer_peca(xOrigem, yOrigem, xDestino, yDestino, peca, peca_inimiga);
                visualizar();
                jogadorAtual = !jogadorAtual;
                return;
            }

            // Verifica se a posição de origem contém uma peça e a posição de destino está vazia
            if (marcacao[yOrigem][xOrigem] != Pecas.LIVRE && marcacao[yDestino][xDestino] == Pecas.LIVRE) {
                if (teste_jogada(xOrigem, yOrigem, xDestino, yDestino)) {
                    marcacao[yOrigem][xOrigem] = Pecas.LIVRE;
                    marcacao[yDestino][xDestino] = peca;
                    visualizar();
                    jogadorAtual = !jogadorAtual;
                    return;
                }
            }

            System.out.println("Erro ao realizar essa jogada, tente novamente!");
            visualizar();
        }
    }



    public boolean teste_jogada(int xOrigem, int yOrigem, int xDestino, int yDestino){
        if (Math.abs(xOrigem - xDestino) > 1 || Math.abs(yOrigem - yDestino) > 1) {
            System.out.println("Movimento inválido.");
            return false;
        }
        if (xDestino < 0 || yDestino < 0 || xDestino > 7 || yDestino > 7) {
            System.out.println("Movimento inválido.");
            return false;
        }
        if(xDestino == xOrigem || yDestino == yOrigem ) {
            System.out.println("Movimento inválido.");
            return false;
        }

        else
            return true;
    }

    public static boolean JogadorAtual() {
        return jogadorAtual;
    }

    public void limpar()
    {
        for (int x = 0; x < LINHAS; x++)
        {
            for (int y = 0; y < COLUNAS; y++)
            {
                marcacao[x][y] = Pecas.LIVRE;
            }
        }
    }
}
