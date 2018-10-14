package to.lucaspg.myclock;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Relogio extends Geometria {

    private float[] vet_coords = null;
    private FloatBuffer buffer = null;

    public Relogio(GL10 openGL, int tamanho) {
        super();

        this.setTamanho(tamanho);
        openGL.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        this.setOpenGL(openGL);

    }

    public void desenha() {

        int tamanho = this.getTamanho();

        float[] vetCoords1 = {

                -tamanho, -tamanho,
                -tamanho, tamanho,
                tamanho, -tamanho,
                tamanho, tamanho};//quadrado de fundo

        //Cria o vetor de coordenadas
        FloatBuffer buffer1 = GeraBuffer.generateBuffer(vetCoords1);
        //refistra as coordenadas na maquina OPENGL
        getOpenGL().glVertexPointer(2, GL10.GL_FLOAT, 0, buffer1);
        getOpenGL().glLoadIdentity();
        getOpenGL().glTranslatef(getPosX(), getPosY(), getPosZ());
        getOpenGL().glRotatef(getRotationAngle(), getRotationX(), getRotationY(), getRotationZ());//rotaciona angulo graus em cima de z
        getOpenGL().glScalef(getScaleX(), getScaleY(), getScaleZ());
        getOpenGL().glColor4f(getRed(), getGreen(), getBlue(), getAlfa());
        getOpenGL().glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }
}