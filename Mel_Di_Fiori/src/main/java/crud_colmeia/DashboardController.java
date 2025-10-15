package crud_colmeia;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import model.Animais;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.DecimalFormat; // Import necessário para formatar a porcentagem

/**
 * Controller que carrega estatísticas e gera o gráfico dinâmico
 * para o Dashboard do Adestrador.
 */
public class DashboardController {

    // Labels mapeadas dos cards de estatísticas
    @FXML private Label lblTotalAnimais;
    @FXML private Label lblAnimaisSaudaveis;
    @FXML private Label lblAnimaisDoentes;
    @FXML private Label lblAdestrados;
    @FXML private Label lblEmAdestramento;

    // Componentes para o Gráfico Dinâmico
    @FXML private ComboBox<String> comboOpcoesGrafico;
    @FXML private PieChart pieChartStatus;
    
    // Armazena a lista completa para reuso (evita consultas repetidas ao banco)
    private List<Animais> todosAnimais; 
    
    // Formatador para as porcentagens (duas casas decimais)
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    public void initialize() {
        // 1. Carregar todos os dados do banco
        carregarEstatisticas();
        
        // 2. Configurar o ComboBox de opções
        comboOpcoesGrafico.getItems().addAll("Situação (Saudáveis/Doentes)", "Status de Adestramento");

        // 3. Adicionar o Listener: Ação ao mudar a opção do ComboBox
        comboOpcoesGrafico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                atualizarGrafico(newValue);
            }
        });
        
        // 4. Selecionar uma opção inicial para preencher o gráfico
        if (!comboOpcoesGrafico.getItems().isEmpty()) {
            comboOpcoesGrafico.getSelectionModel().selectFirst();
        }
    }

    /**
     * Busca todos os animais, atualiza os contadores dos cards e armazena a lista.
     * (Mantido inalterado, exceto pela aplicação das correções de capitalização sugeridas)
     */
    private void carregarEstatisticas() {
        try {
            // Obtém todos os animais (limite alto para estatísticas)
            todosAnimais = new DAO<>(Animais.class).obterTodos(9999, 0); 
            
            // 1. CALCULAR TOTAIS E ATUALIZAR CARDS
            long total = todosAnimais.size();
            lblTotalAnimais.setText(String.valueOf(total));

            // Filtros e contagens para os cards (usando equalsIgnoreCase para robustez)
            long saudaveis = todosAnimais.stream()
                                .filter(a -> "Saudavel".equalsIgnoreCase(a.getStatus()))
                                .count();
            lblAnimaisSaudaveis.setText(String.valueOf(saudaveis));

            long doentes = todosAnimais.stream()
                                .filter(a -> "doente".equalsIgnoreCase(a.getStatus()))
                                .count();
            lblAnimaisDoentes.setText(String.valueOf(doentes));

            long adestrados = todosAnimais.stream()
                                .filter(a -> "adestrado".equalsIgnoreCase(a.getStatus()))
                                .count();
            lblAdestrados.setText(String.valueOf(adestrados));

            long emAdestramento = todosAnimais.stream()
                                .filter(a -> "Em adestramento".equalsIgnoreCase(a.getStatus()))
                                .count();
            lblEmAdestramento.setText(String.valueOf(emAdestramento));

        } catch (Exception e) {
            e.printStackTrace();
            // Tratar erro em todos os labels
            lblTotalAnimais.setText("ERRO");
            lblAnimaisSaudaveis.setText("ERRO");
            lblAnimaisDoentes.setText("ERRO");
            lblAdestrados.setText("ERRO");
            lblEmAdestramento.setText("ERRO");
        }
    }

    /**
     * Atualiza o gráfico de pizza com base na opção selecionada, 
     * agora incluindo o cálculo e a exibição das porcentagens.
     * @param opcao A opção selecionada no ComboBox.
     */
    private void atualizarGrafico(String opcao) {
        if (todosAnimais == null || todosAnimais.isEmpty()) {
            pieChartStatus.setTitle("Nenhum dado para exibir.");
            pieChartStatus.setData(FXCollections.observableArrayList());
            return;
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Long> contagem;
        List<String> statusFiltrados; // Usaremos isto para a filtragem consistente

        if ("Situação (Saudáveis/Doentes)".equals(opcao)) {
            // Status esperados em minúsculas
            statusFiltrados = List.of("saudavel", "doente");
            pieChartStatus.setTitle("Distribuição por Situação de Saúde");

        } else if ("Status de Adestramento".equals(opcao)) {
            // Status esperados em minúsculas
            statusFiltrados = List.of("adestrado", "em adestramento");
            pieChartStatus.setTitle("Distribuição por Adestramento");
            
        } else {
            // Caso de fallback
            pieChartStatus.setTitle("Selecione uma categoria");
            pieChartStatus.setData(FXCollections.observableArrayList());
            return;
        }
        
        // 1. Obter a contagem dos dados filtrados
        contagem = todosAnimais.stream()
            .filter(a -> statusFiltrados.contains(a.getStatus().toLowerCase())) // Filtra de forma consistente
            .collect(Collectors.groupingBy(Animais::getStatus, Collectors.counting()));

        // 2. Calcular o total para os dados do gráfico
        double totalGrafico = contagem.values().stream().mapToLong(Long::longValue).sum();

        // 3. Adicionar os dados contados ao gráfico, incluindo a porcentagem no nome
        contagem.forEach((status, count) -> {
            if (totalGrafico > 0) {
                // Calcula a porcentagem
                double percentual = (count * 100.0) / totalGrafico;
                // Formata o nome: "Status (XX.XX%)"
                String nomeFormatado = status + " (" + df.format(percentual) + "%)";
                pieChartData.add(new PieChart.Data(nomeFormatado, count));
            } else {
                // Caso o total seja zero, adiciona o status sem porcentagem (apenas por segurança)
                pieChartData.add(new PieChart.Data(status, count));
            }
        });
        
        pieChartStatus.setData(pieChartData);
    }
}