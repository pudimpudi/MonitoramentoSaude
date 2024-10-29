import java.util.*;

class Paciente {
    String nome;
    int idade;
    String sexo;

    public Paciente(String nome, int idade, String sexo) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
    }
}

class Local {
    String nome;

    public Local(String nome) {
        this.nome = nome;
    }
}

class Doenca {
    String nome;

    public Doenca(String nome) {
        this.nome = nome;
    }
}

class Intervencao {
    String nome;

    public Intervencao(String nome) {
        this.nome = nome;
    }
}

class Aresta {
    Paciente paciente;
    Local local;
    Doenca doenca;
    Intervencao intervencao;
    double peso;

    public Aresta(Paciente paciente, Local local, double peso) {
        this.paciente = paciente;
        this.local = local;
        this.peso = peso;
    }

    public Aresta(Paciente paciente, Doenca doenca, double peso) {
        this.paciente = paciente;
        this.doenca = doenca;
        this.peso = peso;
    }
}

class Grafo {
    private Map<Paciente, List<Aresta>> listaAdjacencia;

    public Grafo() {
        this.listaAdjacencia = new HashMap<>();
    }

    public void adicionarPaciente(Paciente paciente) {
        listaAdjacencia.putIfAbsent(paciente, new ArrayList<>());
    }

    public void adicionarAresta(Paciente paciente, Local local, double peso) {
        adicionarPaciente(paciente);
        listaAdjacencia.get(paciente).add(new Aresta(paciente, local, peso));
    }

    public void adicionarAresta(Paciente paciente, Doenca doenca, double peso) {
        adicionarPaciente(paciente);
        listaAdjacencia.get(paciente).add(new Aresta(paciente, doenca, peso));
    }

    public List<Paciente> bfs(Paciente inicio) {
        List<Paciente> resultado = new ArrayList<>();
        Queue<Paciente> fila = new LinkedList<>();
        Set<Paciente> visitados = new HashSet<>();

        fila.add(inicio);
        visitados.add(inicio);

        while (!fila.isEmpty()) {
            Paciente atual = fila.poll();
            resultado.add(atual);

            for (Aresta aresta : listaAdjacencia.get(atual)) {
                if (aresta.local != null) { // Conexão com local
                    System.out.println(atual.nome + " visitou " + aresta.local.nome);
                }
                if (aresta.doenca != null) { // Conexão com doença
                    System.out.println(atual.nome + " contraiu " + aresta.doenca.nome);
                }
                Paciente vizinho = aresta.paciente;
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }
        return resultado;
    }

    public void dfs(Paciente inicio) {
        Set<Paciente> visitados = new HashSet<>();
        dfsAuxiliar(inicio, visitados);
    }

    private void dfsAuxiliar(Paciente paciente, Set<Paciente> visitados) {
        if (visitados.contains(paciente)) {
            return;
        }
        visitados.add(paciente);
        System.out.println("Visitando: " + paciente.nome);

        for (Aresta aresta : listaAdjacencia.get(paciente)) {
            if (aresta.local != null) {
                System.out.println(paciente.nome + " visitou " + aresta.local.nome);
            }
            if (aresta.doenca != null) {
                System.out.println(paciente.nome + " contraiu " + aresta.doenca.nome);
            }
            dfsAuxiliar(aresta.paciente, visitados);
        }
    }
}

public class MonitoramentoSaudePublica {
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        Paciente pacienteA = new Paciente("Paciente A", 30, "M");
        Paciente pacienteB = new Paciente("Paciente B", 25, "F");
        Local hospitalX = new Local("Hospital X");
        Local escolaY = new Local("Escola Y");
        Doenca doenca1 = new Doenca("Doença 1");
        Doenca doenca2 = new Doenca("Doença 2");
        Intervencao vacinaZ = new Intervencao("Vacina Z");

        // Adicionando arestas
        grafo.adicionarAresta(pacienteA, hospitalX, 1.0);
        grafo.adicionarAresta(pacienteA, doenca1, 0.8);
        grafo.adicionarAresta(pacienteB, escolaY, 1.0);
        grafo.adicionarAresta(pacienteB, doenca2, 0.7);

        // Executar BFS
        System.out.println("BFS começando pelo Paciente A:");
        List<Paciente> resultadoBFS = grafo.bfs(pacienteA);
        for (Paciente p : resultadoBFS) {
            System.out.println("Paciente encontrado: " + p.nome);
        }

        // Executar DFS
        System.out.println("\nDFS começando pelo Paciente A:");
        grafo.dfs(pacienteA);
    }
}
