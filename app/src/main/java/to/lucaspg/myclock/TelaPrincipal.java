package to.lucaspg.myclock;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TelaPrincipal extends AppCompatActivity {

    //1 - DECLARA UMA REFERENCIA PARA A SUP. DE DESENHO
    private GLSurfaceView superficieDesenho = null;
    private Renderizador render = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //METODO CHAMADO QUANDO O APP E INICIADO
        super.onCreate(savedInstanceState);

        //2 - INSTACIA UM OBJETO DA SUP. DE DESENHO
        this.superficieDesenho = new GLSurfaceView(this);
        //DECLARA UMA REFERENCIA PARA O RENDER'

        //3 - INSTANCIA UM OBJETO RENDERIZADOR
        this.render = new Renderizador();

        //4 - CONFIGURA O OBJETO DE DESENHO DA SUPERFICIE
        superficieDesenho.setRenderer(this.render);
        superficieDesenho.setOnTouchListener(this.render);

        //5 - PUBLICAR A SUP. DESENHO NA TELA
        setContentView(superficieDesenho);

    }
}