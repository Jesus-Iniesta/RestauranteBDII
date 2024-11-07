import re
import datetime
import random

def obtener_vocal_interna(nombre):
    """Obtiene la primera vocal interna en un nombre/apellido."""
    for letra in nombre[1:]:  # Empezamos desde la segunda letra
        if letra in 'AEIOU':
            return letra
    return 'X'  # Si no hay vocales internas

def obtener_consonante_interna(nombre):
    """Obtiene la primera consonante interna en un nombre/apellido."""
    for letra in nombre[1:]:  # Empezamos desde la segunda letra
        if letra not in 'AEIOU':
            return letra
    return 'X'  # Si no hay consonantes internas

def generar_curp(nombre,apellido_paterno,apellido_materno,fecha_nacimiento,estado):
    sexo = ['M','H']
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
    
    # 1. primeras letras
    curp = obtener_vocal_interna(apellido_paterno)#primera vocal del apellido paterno
    curp += apellido_paterno[0] #primer letra del apellido
    curp += apellido_materno[0] # Primera letra del segundo apellido
    curp += nombre[0]
    
    # 2. Fecha de nacimiento en formato AAMMDD
    fecha_nacimiento = datetime.datetime.strptime(fecha_nacimiento, "%Y-%m-%d")
    curp += fecha_nacimiento.strftime("%y%m%d")
    
    #3. Sexo
    curp += random.choice(sexo)
    
    #4. estado nacimiento
    pais = estado.upper().split()
    pais = pais[0][:2]
    curp += pais
    
    #5 Constantes internas de apellido paterno, materno 
    curp += obtener_consonante_interna(apellido_paterno)
    curp += obtener_consonante_interna(apellido_materno)
    curp += obtener_consonante_interna(nombre)
    
    #6
    curp += '0' +str(random.randint(1,9))
    return curp