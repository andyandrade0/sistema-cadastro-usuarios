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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

// Tela de login
public class TelaLogin extends JFrame {

    // cores da tela
    private static final Color COR_FUNDO = new Color(0xFFF5F8);
    private static final Color COR_TITULO = new Color(0x8E3B5B);
    private static final Color COR_SUAVE = new Color(0x9A6580);
    private static final Color COR_BORDA = new Color(0xF3C1D3);
    private static final Color COR_DESTAQUE = new Color(0xD4608B);
    private static final Color COR_DESTAQUE_HOVER = new Color(0xC04D79);
    private static final Color COR_SECUNDARIO = new Color(0xFDE4ED);
    private static final Color COR_SECUNDARIO_HOVER = new Color(0xFAD0DF);

    // fontes da tela
    private static final Font FONTE_TITULO = new Font(Font.SANS_SERIF, Font.BOLD, 22);
    private static final Font FONTE_SUBTITULO = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private static final Font FONTE_ROTULO = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    private static final Font FONTE_CAMPO = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONTE_BOTAO = new Font(Font.SANS_SERIF, Font.BOLD, 13);

    // componentes usados nos eventos
    private final JPanel panelTela;
    private final JTextField txtUsuario;
    private final JPasswordField pswSenha;
    private boolean usuarioValido;

    public TelaLogin() {
        // configurações da janela
        setTitle("Login - Desenvolvimento para Servidores");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // painel principal sem layout (posições manuais)
        panelTela = new JPanel();
        panelTela.setBackground(COR_FUNDO);
        panelTela.setPreferredSize(new Dimension(400, 306));
        panelTela.setLayout(null);
        setContentPane(panelTela);

        // título
        JLabel lblIdentificacao = new JLabel("Bem-vindo(a)", SwingConstants.CENTER);
        lblIdentificacao.setBounds(0, 26, 400, 34);
        lblIdentificacao.setFont(FONTE_TITULO);
        lblIdentificacao.setForeground(COR_TITULO);
        panelTela.add(lblIdentificacao);

        // subtítulo
        JLabel lblSubtitulo = new JLabel("Entre com o seu usuário e senha", SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 62, 400, 18);
        lblSubtitulo.setFont(FONTE_SUBTITULO);
        lblSubtitulo.setForeground(COR_SUAVE);
        panelTela.add(lblSubtitulo);

        // campo usuário
        panelTela.add(criaRotuloCampo("Usuário", 100));

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 120, 320, 32);
        txtUsuario.setColumns(10);
        estilizaCampo(txtUsuario);
        panelTela.add(txtUsuario);

        // campo senha
        panelTela.add(criaRotuloCampo("Senha", 160));

        pswSenha = new JPasswordField();
        pswSenha.setBounds(40, 180, 320, 32);
        estilizaCampo(pswSenha);
        panelTela.add(pswSenha);

        // botões
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(210, 238, 150, 40);
        estilizaBotao(btnEntrar, COR_DESTAQUE, COR_DESTAQUE_HOVER, Color.WHITE, COR_DESTAQUE);
        panelTela.add(btnEntrar);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(40, 238, 150, 40);
        estilizaBotao(btnCadastrar, COR_SECUNDARIO, COR_SECUNDARIO_HOVER, COR_TITULO, COR_BORDA);
        panelTela.add(btnCadastrar);

        // botão Entrar: valida os campos e consulta o banco
        btnEntrar.addActionListener((ActionEvent e) -> {
            String senhaDigitada = new String(pswSenha.getPassword());

            // guarda o que foi digitado
            Usuario usu = new Usuario();
            usu.setUsuario(txtUsuario.getText());
            usu.setSenha(senhaDigitada);

            if ("".equals(txtUsuario.getText())) {
                // usuário vazio
                JOptionPane.showMessageDialog(null,
                        "Campo usuário precisa ser informado!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                txtUsuario.grabFocus();
            } else if ("".equals(senhaDigitada)) {
                // senha vazia
                JOptionPane.showMessageDialog(null,
                        "Campo senha precisa ser informado!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                pswSenha.grabFocus();
            } else {
                // consulta o usuário no banco
                usuarioValido = usu.verificaUsuario(usu.getUsuario(), usu.getSenha());

                if (usuarioValido) {
                    // usuário encontrado: avisa e abre o menu
                    JOptionPane.showMessageDialog(null,
                            "Usuário encontrado",
                            "Atenção", JOptionPane.INFORMATION_MESSAGE);
                    TelaInicio telaInicio = new TelaInicio();
                    telaInicio.abreTela();
                    dispose();
                } else {
                    // usuário não encontrado: avisa e limpa os campos
                    JOptionPane.showMessageDialog(null,
                            "Usuário inválido ou inexistente",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    limpaText();
                    txtUsuario.grabFocus();
                }
            }
        });

        // botão Cadastrar: abre a tela de cadastro
        btnCadastrar.addActionListener((ActionEvent e) -> {
            TelaCadastro telaCadastro = new TelaCadastro();
            telaCadastro.abreTela();
            dispose();
        });

        // ajusta o tamanho e centraliza a janela
        pack();
        setLocationRelativeTo(null);
    }

    // cria o rótulo de um campo
    private static JLabel criaRotuloCampo(String texto, int y) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setBounds(40, y, 320, 18);
        rotulo.setFont(FONTE_ROTULO);
        rotulo.setForeground(COR_TITULO);
        return rotulo;
    }

    // aplica o estilo nos campos de texto
    private static void estilizaCampo(JTextField campo) {
        campo.setFont(FONTE_CAMPO);
        campo.setForeground(COR_TITULO);
        campo.setBackground(Color.WHITE);
        campo.setCaretColor(COR_DESTAQUE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
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

    // limpa os campos usuário e senha
    public void limpaText() {
        txtUsuario.setText("");
        pswSenha.setText("");
    }

    // mostra a tela de login
    public void abreTela() {
        TelaLogin tela = new TelaLogin();
        tela.setVisible(true);
    }
}
