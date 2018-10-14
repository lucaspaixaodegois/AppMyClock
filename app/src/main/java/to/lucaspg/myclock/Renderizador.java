package to.lucaspg.myclock;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//CLASSE QUE IRA IMPLEMENTAR A LOGICA DO DESENHO
class Renderizador implements GLSurfaceView.Renderer, GLSurfaceView.OnTouchListener {

    private float largura;
    private float altura;
    private float posX;
    private float posY;
    //int tamanho = 400;
    private int angulo = 0;
    private Relogio relogio;
    private Horas horas;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int heigth) {

        largura = width;
        altura = heigth;

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, largura, 0, altura, 1, -1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        //CONFIGURANDO A AREA DE VISUALIZACAO DA TELA NO DISP
        gl.glViewport(0, 0, width, heigth);

        //Habilita o desenho por vertices
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        relogio = new Relogio(gl, 200);
        relogio.setCor(1, 0, 0, 0);
        relogio.setScale(1f, 1f, 1f);//tamanho do quadrado principal

    }

    FloatBuffer generateBuffer(float[] vetor) {
        ByteBuffer prBuffer = ByteBuffer.allocateDirect(vetor.length * 4);//aloca memoria em bytes
        //ordena os endereços de memoria conforme a arquitetura do processador
        prBuffer.order(ByteOrder.nativeOrder());
        //gera o encapsulador, limpa, insere o vetor. REtira eventuais sobras de memoria
        FloatBuffer prFloat = prBuffer.asFloatBuffer();
        prFloat.clear();//limpa possiveis lixos
        prFloat.put(vetor);//encapsula
        prFloat.flip();//limpa e retira eventuais sobras de memorias
        return prFloat;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //APLICA A COR DE LIMPEZA DE TELA A TODOS OS BITS DO BUFFER DE COR
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();// Carrega a matriz identidade

        gl.glPushMatrix();
        gl.glTranslatef(0, 405, 0);//tamanho da distancia entre os 2 quadrados
        gl.glPopMatrix();

        for (int cont = 0; cont < 300; cont++) {

            relogio.desenha();
            relogio.setTranslate(posX, posY);
            relogio.setRotation(angulo);
            angulo += 1;
        }

//            gl.glTranslatef(posX, posY, 0.0f);
//            gl.glScalef(1f, 1f, 1f);//tamanho do quadrado principal
//            gl.glColor4f(1, 0, 0, 1.0f);//configura a cor do quadrado de dentro
//            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);//monta o desenho de triangulo
//            gl.glRotatef(angulo, 0, 0, 1);//roda em torno do vermelho

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //CHAMADO QUANDO A SUP. DE DESENHO E CRIADA
        long tempoInicial = System.currentTimeMillis();
        long tempoAtual = System.currentTimeMillis();
        //CONFIGURA A COR DE LIMPEZA NO FORMATO RGBA
        gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean onTouch(View superficeDesenho, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            posX = (int) event.getX();
            posY = altura - (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //HOUBE UM TERMINO DE TOQUE
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //HOUVE UM TOQUE NA TELA
        }
        return true;
    }
}





















/*
    private int angulo = 0, anguloSegundo = 0, anguloMinuto = 30, anguloHora = 23;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) { //chamado quando a superficie de desenho é criada
        gl.glClearColor(1, 1, 1, 1);//configura a cor de limpeza no formato RGBA
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) { //chamado quando a superficie de desenho sofre  alguma alteração

        altura = height;
        largura = width;

        gl.glViewport(0, 0, width, height); //mapeia a cena inteira
        gl.glMatrixMode(GL10.GL_PROJECTION); //configurando a area de coordenadas do plano cartesiano
        gl.glLoadIdentity();//substitui a matriz atual pela matriz de identidade.
        gl.glOrthof(0, width, 0, height, -1, 1);//definem os limites da projeção. no  2D, define a área visível da tela em unidades OpenGL.
        gl.glMatrixMode(GL10.GL_MODELVIEW);//Especifica o destino das operações das matriz subsequentes.
        gl.glLoadIdentity();//substitui a matriz atual pela matriz de identidade.

        //CONFIGURANDO A AREA DE VISUALIZACAO DA TELA NO DISP
        gl.glViewport(0, 0, width, height);

        //Habilita o desenho por vertices
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        relogio = new Relogio(gl, 200);
        relogio.setCor(1, 1, 0, 0);
        relogio.setRotation(angulo);
        relogio.setTranslate(posX, posY);
        relogio.setScale(1f, 1f, 1f);//tamanho do quadrado principal
        //  gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);//monta o desenho de triangulo

        gl.glColor4f(0, 1, 0, 1);
        gl.glRotatef(angulo, 0, 0, 1);//roda em torno do vermelho
        gl.glTranslatef(0, 405, 0);//tamanho da distancia entre os 2 quadrados
        //gl.glRotatef(angulo, 0, 0, 3);//aki
        gl.glScalef(1f, 1f, 1f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl.glPushMatrix();
        gl.glColor4f(1, 1, 0, 1);
        //gl.glRotatef(angulo, 0, 0, 1);
        gl.glTranslatef(0, 405, 0);//tamanho da distancia entre os 2 quadrados
        // gl.glRotatef(angulo, 0, 0, 3);
        gl.glScalef(1f, 1f, 1f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        //hora
        hora = new Horas(gl, 120);
        hora.setTranslate(posX, posY);
        hora.setCor(1, 0, 0, 1);
        hora.setRotation(0);

        //Minuto
        minuto = new Minutos(gl, 120);
        minuto.setTranslate(775, 665);
        minuto.setCor(1, 0, 0, 1);
        minuto.setRotation(0);

        //Segundo
        segundo = new Segundos(gl, 120);
        segundo.setTranslate(775, 665);
        segundo.setCor(1, 0, 0, 1);
        segundo.setRotation(0);



    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glPushMatrix();
        relogio.desenha();
        //    minuto.desenha();
        //   segundo.desenha();
    }

    @Override
    public boolean onTouch(View superficeDesenho, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            posX = (int) event.getX();
            posY = altura - (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //HOUBE UM TERMINO DE TOQUE
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //HOUVE UM TOQUE NA TELA
        }
        return true;
    }
}


    /*
           float[] vetCoords1 =
                   {
                           -tamanho, -tamanho,
                           -tamanho, tamanho,
                           tamanho, -tamanho,
                           tamanho, tamanho};//quadrado de fundo


           float[] vetCoords2 =
                   {
                           -5, tamanho / 2,
                           -5, 0,
                           tamanho / 7, tamanho / 2,
                           tamanho / 7, 0};//retangulo Segundo


           FloatBuffer buffer1 = generateBuffer(vetCoords1);//transforma o vetor num floatbuffer
           FloatBuffer buffer2 = generateBuffer(vetCoords2);//transforma o vetor num floatbuffer

           gl.glVertexPointer(2, GL10.GL_FLOAT, 0, buffer1);
           gl.glVertexPointer(2, GL10.GL_FLOAT, 0, buffer2);

       }

       FloatBuffer generateBuffer(float[] vetor) {

           ByteBuffer prBuffer = ByteBuffer.allocateDirect(vetor.length * 4);//aloca memoria em bytes
           //ordena os endereços de memoria conforme a arquitetura do processador
           prBuffer.order(ByteOrder.nativeOrder());
           //gera o encapsulador, limpa, insere o vetor. REtira eventuais sobras de memoria
           FloatBuffer prFloat = prBuffer.asFloatBuffer();
           prFloat.clear();//limpa possiveis lixos
           prFloat.put(vetor);//encapsula
           prFloat.flip();//limpa e retira eventuais sobras de memorias
           return prFloat;

       }

       @Override
       public void onDrawFrame(GL10 gl) {

           //APLICA A COR DE LIMPEZA DE TELA A TODOS OS BITS DO BUFFER DE COR
           gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
           gl.glLoadIdentity();// Carrega a matriz identidade
           //FUNCAO DE TRANSLACAO

           gl.glTranslatef(posX, posY, 0.0f);

           for (int cont = 0; cont < 41; cont++) {
               gl.glPushMatrix();

               gl.glScalef(1f, 1f, 1f);//tamanho do quadrado principal
               gl.glColor4f(1, 0, 0, 1.0f);//configura a cor do quadrado de dentro
               gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);//monta o desenho de triangulo
               gl.glRotatef(angulo, 0, 0, 1);//roda em torno do vermelho
               angulo += 1;
           }

           //for (int cont = 0; cont < 61; cont++) {
               gl.glPopMatrix();
               gl.glScalef(0.5f, 0.5f, 1f);//tamanho do quadrado principal
               gl.glColor4f(0, 1, 0, 1);
               gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);//monta o desenho de triangulo
               gl.glRotatef(anguloSegundo * 6, 0, 0, 1);//roda em torno do vermelho

               gl.glScalef(0.5f, 0.5f, 1);//tamanho do quadrado principal
               gl.glColor4f(0, 0, 1, 1);
               gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);//monta o desenho de triangulo
               gl.glRotatef(anguloMinuto * 6, 0, 0, 1);//roda em torno do vermelho

               gl.glScalef(0.5f, 0.5f, 1);//tamanho do quadrado principal
               gl.glColor4f(1, 1, 0, 1);
               gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);//monta o desenho de triangulo
               gl.glRotatef(anguloHora * 30, 0, 0, 1);//roda em torno do vermelho
         //  }

       }

       @Override
       public void onSurfaceCreated(GL10 gl, EGLConfig config) {
           //CHAMADO QUANDO A SUP. DE DESENHO E CRIADA
           long tempoInicial = System.currentTimeMillis();
           long tempoAtual = System.currentTimeMillis();

           //CONFIGURA A COR DE LIMPEZA NO FORMATO RGBA
           gl.glClearColor(0.0f, 0.0f, 0, 1.0f);
       }*/