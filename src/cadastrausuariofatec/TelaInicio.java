package cadastrausuariofatec;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

// Tela inicial (menu) depois do login
public class TelaInicio extends JFrame {

    // cores da tela
    private static final Color COR_FUNDO = new Color(0xFFF5F8);
    private static final Color COR_TITULO = new Color(0x8E3B5B);
    private static final Color COR_SUAVE = new Color(0x9A6580);
    private static final Color COR_BORDA = new Color(0xF3C1D3);
    private static final Color COR_DESTAQUE = new Color(0xD4608B);
    private static final Color COR_DESTAQUE_HOVER = new Color(0xC04D79);
    private static final Color COR_SECUNDARIO = new Color(0xFDE4ED);
    private static final Color COR_SECUNDARIO_HOVER = new Color(0xFAD0DF);
    // cores do botão Excluir
    private static final Color COR_EXCLUIR = new Color(0xB5476B);
    private static final Color COR_EXCLUIR_BORDA = new Color(0xE9A6BE);

    // fontes da tela
    private static final Font FONTE_SAUDACAO = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private static final Font FONTE_SUBTITULO = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private static final Font FONTE_BOTAO = new Font(Font.SANS_SERIF, Font.BOLD, 13);

    private final JPanel tela;
    private boolean exclusaoValida;

    public TelaInicio() {
        // configurações da janela
        setTitle("Menu - Desenvolvimento para Servidores");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // painel principal sem layout (posições manuais)
        tela = new JPanel();
        tela.setBackground(COR_FUNDO);
        tela.setPreferredSize(new Dimension(400, 240));
        tela.setLayout(null);
        setContentPane(tela);

        // boas-vindas com o nome do usuário logado
        JLabel lblUsuario = new JLabel("Bem vindo " + Usuario.nomeUsuario, SwingConstants.CENTER);
        lblUsuario.setBounds(20, 32, 360, 32);
        lblUsuario.setFont(FONTE_SAUDACAO);
        lblUsuario.setForeground(COR_TITULO);
        tela.add(lblUsuario);

        // subtítulo
        JLabel lblSubtitulo = new JLabel("Deseja realizar algum dos seguintes passos?", SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 68, 400, 18);
        lblSubtitulo.setFont(FONTE_SUBTITULO);
        lblSubtitulo.setForeground(COR_SUAVE);
        tela.add(lblSubtitulo);

        // botões
        JButton btnAlterar = new JButton("Alterar Dados");
        btnAlterar.setBounds(40, 118, 320, 40);
        estilizaBotao(btnAlterar, COR_DESTAQUE, COR_DESTAQUE_HOVER, Color.WHITE, COR_DESTAQUE);
        tela.add(btnAlterar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(40, 170, 150, 40);
        estilizaBotao(btnVoltar, COR_SECUNDARIO, COR_SECUNDARIO_HOVER, COR_TITULO, COR_BORDA);
        tela.add(btnVoltar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(210, 170, 150, 40);
        estilizaBotao(btnExcluir, Color.WHITE, COR_SECUNDARIO, COR_EXCLUIR, COR_EXCLUIR_BORDA);
        tela.add(btnExcluir);

        // botão Voltar: volta para o login
        btnVoltar.addActionListener((ActionEvent e) -> {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.abreTela();
            dispose();
        });

        // botão Alterar Dados: abre a tela de alteração
        btnAlterar.addActionListener((ActionEvent e) -> {
            TelaAlteracao telaAlterar = new TelaAlteracao();
            telaAlterar.abreTela();
            dispose();
        });

        // botão Excluir: confirma e exclui o usuário
        btnExcluir.addActionListener((ActionEvent e) -> {
            // pergunta se quer excluir (0 = Sim)
            Object[] options = { "Sim", "Não" };
            int opcao = JOptionPane.showOptionDialog(null,
                    Usuario.nomeUsuario + ", deseja se excluir?", "Atenção",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);

            if (opcao == 0) {
                // exclui no banco
                Usuario usu = new Usuario();
                exclusaoValida = usu.excluiUsuario(Usuario.usuarioSistema);

                if (exclusaoValida) {
                    // excluiu: avisa e volta para o login
                    JOptionPane.showMessageDialog(null,
                            "Usuário deletado corretamente, voltaremos a tela de login.",
                            "Atenção", JOptionPane.INFORMATION_MESSAGE);
                    TelaLogin tLogin = new TelaLogin();
                    tLogin.abreTela();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Erro ao tentar se conectar ao Banco de Dados.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ajusta o tamanho e centraliza a janela
        pack();
        setLocationRelativeTo(null);
    }

    // aplica o estilo nos botões e troca a cor ao passar o mouse
    private static void estilizaBotao(JButton botao, Color fundo, Color fundoHover,
            Color texto, Color borda) {
        botao.setUI(new BasicButtonUI());
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(texto);
        botao.setBackground(fundo);
        botao.setOpaque(true);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(borda, 1));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(fundoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(fundo);
            }
        });
    }

    // mostra a tela inicial
    public void abreTela() {
        TelaInicio telaInicio = new TelaInicio();
        telaInicio.setVisible(true);
    }
}
