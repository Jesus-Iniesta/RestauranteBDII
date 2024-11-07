import datetime
import random

def generar_fecha_aleatoria():
    # Obtener la fecha actual
    hoy = datetime.date.today()
    
    # Año límite (al menos 18 años de edad)
    año_limite = hoy.year - 18
    
    # Generar un año aleatorio entre 1965 y año límite
    año = random.randint(1965, año_limite)
    
    # Generar un mes aleatorio
    mes = random.randint(1, 12)
    
    # Determinar el último día válido para el mes y año generados
    # Usamos el último día del mes como 28, 29, 30 o 31 según corresponda
    if mes == 12:
        ultimo_dia_mes = 31
    else:
        siguiente_mes = datetime.date(año, mes + 1, 1)
        ultimo_dia_mes = (siguiente_mes - datetime.timedelta(days=1)).day
    
    # Generar un día aleatorio dentro del mes válido
    dia = random.randint(1, ultimo_dia_mes)
    
    # Generar la fecha final
    fecha_nacimiento = datetime.date(año, mes, dia)
    return fecha_nacimiento.strftime("%Y-%m-%d")

