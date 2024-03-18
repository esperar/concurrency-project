package esperer.concurrency.lock

import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

class CustomSpringELParser {

    companion object {
        fun getDynamicValue(parameterNames: Array<String>, vararg args: Any, key: String): Any {
            val parser = SpelExpressionParser()
            val context = StandardEvaluationContext()

            for(i in parameterNames.indices) {
                context.setVariable(parameterNames[i], args[i])
            }

            return parser.parseExpression(key).getValue(context, Object::class)!!
        }
    }
}