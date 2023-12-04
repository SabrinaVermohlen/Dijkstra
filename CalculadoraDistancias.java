import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.*;

public class CalculadoraDistancias {

    private Map<String, Map<String, Integer>> grafo;

    public CalculadoraDistancias() {
        this.grafo = new HashMap<>();
    }

    public void carregarDistancias(String arquivo) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(arquivo))) {

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                System.out.println("Linha lida: " + linha);

                String[] partes = linha.split(";");

                if (partes.length != 3) {
                    System.err.println("Erro: Formato de linha inválido: " + linha);
                    continue;
                }

                String cidadeOrigem = removerAcentos(partes[0].trim());
                String cidadeDestino = removerAcentos(partes[1].trim());
                int distancia = Integer.parseInt(partes[2].trim());

                grafo.computeIfAbsent(cidadeOrigem, k -> new HashMap<>());
                grafo.computeIfAbsent(cidadeDestino, k -> new HashMap<>());

                grafo.get(cidadeOrigem).put(cidadeDestino, distancia);
                grafo.get(cidadeDestino).put(cidadeOrigem, distancia);
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado: " + arquivo);
        }
    }

    private String removerAcentos(String texto) {
        if (texto == null) {
            return null;
        }

        String regex = "\\p{InCombiningDiacriticalMarks}+";
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll(regex, "");
    }

    private Stack<String> criarPilha() {
        Stack<String> pilha = new Stack<>();
        for (String cidade : grafo.keySet()) {
            pilha.push(cidade);
        }
        return pilha;
    }

    private void listarPilha(Stack<String> pilha) {
        System.out.println("Valores na pilha:");
        while (!pilha.isEmpty()) {
            System.out.println(pilha.pop());
        }
    }

    private Queue<String> criarFila() {
        Queue<String> fila = new LinkedList<>();
        for (String cidade : grafo.keySet()) {
            fila.add(cidade);
        }
        return fila;
    }

    private void listarFila(Queue<String> fila) {
        System.out.println("Valores na fila:");
        while (!fila.isEmpty()) {
            System.out.println(fila.poll());
        }
    }

    private int calcularDistanciaDijkstra(String origem, String destino) {
        if (!grafo.containsKey(origem) || !grafo.containsKey(destino)) {
            System.err.println("Cidade de origem ou destino não encontrada no grafo.");
            return -1;
        }

        Map<String, Integer> distancia = new HashMap<>();
        Map<String, Boolean> visitado = new HashMap<>();
        PriorityQueue<String> filaPrioridade = new PriorityQueue<>(Comparator.comparingInt(distancia::get));

        for (String cidade : grafo.keySet()) {
            distancia.put(cidade, Integer.MAX_VALUE);
            visitado.put(cidade, false);
        }

        distancia.put(origem, 0);
        filaPrioridade.add(origem);

        while (!filaPrioridade.isEmpty()) {
            String atual = filaPrioridade.poll();

            if (visitado.get(atual)) {
                continue;
            }

            visitado.put(atual, true);

            for (Map.Entry<String, Integer> vizinho : grafo.get(atual).entrySet()) {
                String cidadeVizinha = vizinho.getKey();
                int pesoAresta = vizinho.getValue();

                if (!visitado.get(cidadeVizinha) && distancia.get(atual) + pesoAresta < distancia.get(cidadeVizinha)) {
                    distancia.put(cidadeVizinha, distancia.get(atual) + pesoAresta);
                    filaPrioridade.add(cidadeVizinha);
                }
            }
        }

        return distancia.get(destino);
    }

    public static void main(String[] args) {
        try {
            String caminhoArquivo = "C:/Users/sabri/Downloads/distancias_cidades.txt";

            CalculadoraDistancias calculadora = new CalculadoraDistancias();
            calculadora.carregarDistancias(caminhoArquivo);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Menu:");
                System.out.println("1) Calcular a distância entre dois pontos");
                System.out.println("2) Criar e listar pilha");
                System.out.println("3) Criar e listar fila");
                System.out.println("4) Sair");

                int escolha = scanner.nextInt();

                if (escolha == 1) {
                    scanner.nextLine();
                    System.out.print("Digite a cidade de origem: ");
                    String origem = calculadora.removerAcentos(scanner.nextLine().trim());

                    System.out.print("Digite a cidade de destino: ");
                    String destino = calculadora.removerAcentos(scanner.nextLine().trim());

                    int distancia = calculadora.calcularDistanciaDijkstra(origem, destino);

                    if (distancia != -1) {
                        System.out.println("A distância entre " + origem + " e " + destino + " é: " + distancia);
                    } else {
                        System.out.println("Não há caminho entre " + origem + " e " + destino);
                    }
                } else if (escolha == 2) {
                    Stack<String> pilha = calculadora.criarPilha();
                    calculadora.listarPilha(pilha);
                } else if (escolha == 3) {
                    Queue<String> fila = calculadora.criarFila();
                    calculadora.listarFila(fila);
                } else if (escolha == 4) {
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
