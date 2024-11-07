import re
import datetime
import random

def generar_rfc(nombre,apellido_paterno,apellido_materno,fecha_nacimiento):
    #Quitar acentos y poner mayúsculas
    def limpiar_texto(texto):
        texto = re.sub(r'[ÁÀÄÂ]', 'A', texto)
        texto = re.sub(r'[ÉÈËÊ]', 'E', texto)
        texto = re.sub(r'[ÍÌÏÎ]', 'I', texto)
        texto = re.sub(r'[ÓÒÖÔ]', 'O', texto)
        texto = re.sub(r'[ÚÙÜÛ]', 'U', texto)
        return texto.upper()
    
    # Limpiar y procesar el nombre y apellidos
    nombre = limpiar_texto(nombre)
    apellido_paterno = limpiar_texto(apellido_paterno)
    apellido_materno = limpiar_texto(apellido_materno)
    
    # Paso 1: Inicial del apellido paterno + primera vocal interna del apellido paterno
    rfc = apellido_paterno[0]
    for letra in apellido_paterno:
        if letra in "AEIOU":
            rfc += letra
            break
    else:
        rfc += 'X' #En caso de no tener una vocal interna (muy raro)
    # Paso 2: Inicial del apellido materno
    rfc += apellido_materno[0]
    # Paso 3: Inicial del primer nombre (o segundo nombre si el primero es José o María)
    rfc += nombre[0]
    # Paso 4: Fecha de nacimiento en formato AAMMDD
    fecha_nacimiento = datetime.datetime.strptime(fecha_nacimiento, "%Y-%m-%d")
    rfc += fecha_nacimiento.strftime("%y%m%d")
    rfc += str(random.randint(0,9))+ str(random.randint(0,9)) + str(random.randint(0,9))
    return rfc