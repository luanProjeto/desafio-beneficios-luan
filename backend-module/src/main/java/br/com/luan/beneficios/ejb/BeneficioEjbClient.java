package br.com.luan.beneficios.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.math.BigDecimal;
import java.util.Hashtable;

public class BeneficioEjbClient {

    private final String jndiName;

    public BeneficioEjbClient(String jndiName) {
        this.jndiName = jndiName;
    }

    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            // Ajuste o factory/provider conforme seu servidor EJB
            // env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            // env.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
            Context ctx = new InitialContext(env);
            Object ref = ctx.lookup(jndiName);
            br.com.luan.beneficios.ejb.BeneficioEjb ejb = (br.com.luan.beneficios.ejb.BeneficioEjb) ref;
            ejb.transferir(origemId, destinoId, valor);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao invocar EJB via JNDI: " + e.getMessage(), e);
        }
    }
}
