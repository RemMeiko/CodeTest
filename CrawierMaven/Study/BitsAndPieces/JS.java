package Study.BitsAndPieces;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;

public class JS {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException, IOException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String jsFileName = ".\\src\\main\\resourcs\\baidufanyi\\Transplate.js";   // 读取js文件
        FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
        engine.eval(reader);

        if(engine instanceof Invocable) {
            Invocable invoke = (Invocable)engine;

            Object sign = invoke.invokeFunction("e","中国");
            // 调用merge方法，并传入两个参数
            //Double c = (Double)invoke.invokeFunction("merge", 2, 3);
            //System.out.println("c = " + c);
            System.out.println(sign);
        }
        reader.close();
    }
}
