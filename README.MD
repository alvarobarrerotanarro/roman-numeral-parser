# Roman Numeral Parser 

Roman numeral parser es una pequeña libreria interfaz de programacion escrita en Java que pretende facilitar la conversion entre numeros Arabes a Romanos y viceversa.

> La libreria esta diseñada para tener un facil uso ademas de validar el formato numeros introducimos.

La clase principal es `RomanNumeral`, que esta diseñada para ser versatil y soportar conversiones bidireccionales; por otro lado tambien existe `RomanNumeralParser`, que es una implementacion de un iterable, esta ultima clase esta pensada para recorrer cada numero romano y convertirlo.

## Interpretar un numero Romano

Para convertir / interpretar un numero Romano en un numero Arabe (un numero de tipo short), podemos construir un `RomanNumeral` y utilizar el metodo `parse()`.

> El constructor de `RomanNumeral` esta pensado para no lanzar excepciones, incluso si el formato es invalido. Las excepciones relacionadas con el formato de los numeros (las cuales cubriremos en unos momentos) se dan al momento de llamar al metodo `parse()`.

```java
import romannumeralparser.RomanNumeral;

public class Main {
    public static void main(String args[]) {
        RomanNumeral numeral = new RomanNumeral("XII");
        short arabic = numeral.parse();
        
        // Imprime: XII - 12
        System.out.printf("%s - %d\n", numeral, arabic);
    }
}
```

## Validar el formato de un numero Romano. 

Antes de convertir el numero y someternos a una posible excepcion relacionada con el formato del numero Romano, podemos validar el formato utilizando el metodo `validate()`.

> Cabe destacar que con numeros Romanos solamente podemos representar numeros Arabes dentro del intervalo [1, 3999], cualquier numero Romano que trate de representar un numero fuera del intervalo, provocara un error de formato.

```java
import romannumeralparser.RomanNumeral;

public class Main {
    public static void main(String args[]) {
        RomanNumeral numeral = new RomanNumeral("MouseHouse");
        
        if (numeral.validate()) {
            // ...
        }  else {
           System.out.printf("Formato invalido: %s\n", numeral);
        }
        
        // Hacer el resto de cosas ...
    }
}
```

## Convertir un numero Arabe en un numero Romano

La libreria considera que un numerao Arabe puede ser representado por cualquier valor numerico compatible con el tipo de dato `short`; por lo tanto, para qué generar una cadena de caracteres cuando lo mismo buscamos realizar operaciones aritmeticas.

He aqui un metodo estatico "fabrica" para un `RomanNumeral` desde un un tipo de dato `short`:

```java
import romannumeralparser.RomanNumeral;

public class Main {
    public static void main(String args[]) {
        RomanNumeral numeral = RomanNumeral.fromArabic(3999);
        System.out.println(numeral);
    }
}
```

> Hay que tener precaucion porque si intentamos convertir un numero por encima de 3999, recibiremos un a excepcion de rango.

Si no recordamos el intervalo de valores numericos validos para los numeros Romanos, podemos acceder a estas dos propiedades de clase:

```java
import romannumeralparser.RomanNumeral;

public class Main {
    public static void main(String args[]) {
        System.out.println(RomanNumeral.MAX_VALUE); // 3999
        System.out.println(RomanNumeral.MIN_VALUE); //    1
    }
}
```

## Excepciones

Existen dos tipos de excepciones:

- `RomanNumeralFormatError`: Ocurre cuando un numero Romano con formato invalido es interpretado.

- `RomanNumeralRangeError`: Ocurre cuando intentamos convertir un numero Arabe fuera del intervalo [1, 3999] a un numero Romano.
