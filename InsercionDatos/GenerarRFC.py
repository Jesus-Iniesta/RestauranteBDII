import re
import datetime
import random

def generar_rfc(nombre,apellido_paterno,apellido_materno,fecha_nacimiento):
    #Quitar acentos y poner mayúsculas
    def limpiar_texto(texto):
        texto = texto.lower()
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
    
    #1 primeras 2 letras del apellido paterno
    rfc = apellido_paterno[0] + apellido_paterno[1]
    #2 primera letra del apellido materno 
    rfc += apellido_materno[0]
    #dos ultimos digitos del año de nacimiento
    fecha_nacimiento = fecha_nacimiento.split('-')
    rfc += fecha_nacimiento[0][2] + fecha_nacimiento[0][3]
    #mes de nacimiento 
    rfc += fecha_nacimiento[1]
    #dia de nacimiento 
    rfc += fecha_nacimiento[2]
    #Generar homoclave 
    rfc += str(random.randint(0,9)) + str(random.randint(1,9)) + str(random.randint(0,9)) 
    return rfc