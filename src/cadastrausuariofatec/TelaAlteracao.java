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

// Tela de alteração de nome e senha
public class TelaAlteracao extends JFrame {

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
    private final JPanel tela;
    private final JTextField txtNome;
    private final JPasswordField passAtual;
    private final JPasswordField passSenha;
    private final JPasswordField confPassSenha;
    private boolean atualizacaoValida;

    public TelaAlteracao() {
        // configurações da janela
        setTitle("Alteração - Desenvolvimento para Servidores");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // painel principal sem layout (posições manuais)
        tela = new JPanel();
        tela.setBackground(COR_FUNDO);
        tela.setPreferredSize(new Dimension(400, 420));
        tela.setLayout(null);
        setContentPane(tela);

        // título
        JLabel lblIdentificacao = new JLabel("Alterar dados", SwingConstants.CENTER);
        lblIdentificacao.setBounds(0, 24, 400, 34);
        lblIdentificacao.setFont(FONTE_TITULO);
        lblIdentificacao.setForeground(COR_TITULO);
        tela.add(lblIdentificacao);

        // subtítulo
        JLabel lblSubtitulo = new JLabel("Atualize o seu nome e a sua senha", SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 60, 400, 18);
        lblSubtitulo.setFont(FONTE_SUBTITULO);
        lblSubtitulo.setForeground(COR_SUAVE);
        tela.add(lblSubtitulo);

        // campo nome
        tela.add(criaRotuloCampo("Nome", 96));

        txtNome = new JTextField();
        txtNome.setBounds(40, 116, 320, 32);
        txtNome.setColumns(10);
        estilizaCampo(txtNome);
        tela.add(txtNome);

        // campo senha atual
        tela.add(criaRotuloCampo("Senha Atual", 156));

        passAtual = new JPasswordField();
        passAtual.setBounds(40, 176, 320, 32);
        estilizaCampo(passAtual);
        tela.add(passAtual);

        // campo nova senha
        tela.add(criaRotuloCampo("Nova Senha", 216));

        passSenha = new JPasswordField();
        passSenha.setBounds(40, 236, 320, 32);
        estilizaCampo(passSenha);
        tela.add(passSenha);

        // campo confirmar senha
        tela.add(criaRotuloCampo("Confirmar Senha", 276));

        confPassSenha = new JPasswordField();
        confPassSenha.setBounds(40, 296, 320, 32);
        estilizaCampo(confPassSenha);
        tela.add(confPassSenha);

        // botões
        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.setBounds(210, 354, 150, 40);
        estilizaBotao(btnAlterar, COR_DESTAQUE, COR_DESTAQUE_HOVER, Color.WHITE, COR_DESTAQUE);
        tela.add(btnAlterar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(40, 354, 150, 40);
        estilizaBotao(btnCancelar, COR_SECUNDARIO, COR_SECUNDARIO_HOVER, COR_TITULO, COR_BORDA);
        tela.add(btnCancelar);

        // botão Cancelar: volta para o menu
        btnCancelar.addActionListener((ActionEvent e) -> {
            TelaInicio telaIni = new TelaInicio();
            telaIni.abreTela();
            dispose();
        });

        // botão Alterar: valida os campos e atualiza no banco
        btnAlterar.addActionListener((ActionEvent e) -> {
            String novaSenha = new String(passSenha.getPassword());

            // guarda o nome e a nova senha
            Usuario usu = new Usuario();
            usu.setNome(txtNome.getText());
            usu.setSenha(novaSenha);

            // validações dos campos
            if ("".equals(usu.getNome())) {
                JOptionPane.showMessageDialog(null,
                        "Campo nome do usuário precisa ser informado!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                txtNome.grabFocus();
            } else if (usu.getNome().length() > 30) {
                JOptionPane.showMessageDialog(null,
                        "O nome deve ter no máximo 30 caracteres!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                txtNome.grabFocus();
            } else if ("".equals(usu.getSenha())) {
                JOptionPane.showMessageDialog(null,
                        "Campo senha precisa ser informado!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                passSenha.grabFocus();
            } else if (!usu.verificaUsuario(Usuario.usuarioSistema,
                    new String(passAtual.getPassword()))) {
                // confere a senha atual no banco
                JOptionPane.showMessageDialog(null,
                        "Senha inválida, verifique!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                passAtual.grabFocus();
            } else if (!novaSenha.equals(new String(confPassSenha.getPassword()))) {
                JOptionPane.showMessageDialog(null,
                        "Campos de Senha e Confirmação não são iguais!",
                        "Atenção", JOptionPane.ERROR_MESSAGE);
                passSenha.grabFocus();
            } else {
                // atualiza no banco
                atualizacaoValida = usu.alteraUsuario(txtNome.getText(), Usuario.usuarioSistema, usu.getSenha());

                if (atualizacaoValida) {
                    // alterou: avisa e volta para o login
                    JOptionPane.showMessageDialog(null,
                            "Dado(s) do usuário alterado(s) retornaremos a tela de login.",
                            "Atenção", JOptionPane.INFORMATION_MESSAGE);
                    TelaLogin tLogin = new TelaLogin();
                    tLogin.abreTela();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Problemas ao atualizar o usuário",
                            "Atenção", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // preenche o nome atual
        txtNome.setText(Usuario.nomeUsuario);

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

    // mostra a tela de alteração
    public void abreTela() {
        TelaAlteracao telaAlteracao = new TelaAlteracao();
        telaAlteracao.setVisible(true);
    }
}
