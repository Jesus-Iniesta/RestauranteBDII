import csv
import psycopg2
import random
import os


#conexion a la base de datos
def connect_to_db():
    return psycopg2.connect(
        host="localhost",
        dbname = "prueba_proyecto",
        user = "postgres",
        password="1234"
    )
def limpiar_pantalla():
    # Limpia la pantalla dependiendo del sistema operativo
    if os.name == 'nt':  # Para Windows
        os.system('cls')
    else:  # Para sistemas Unix (Linux, macOS)
        os.system('clear')
    
# Cargar datos de un archivo CSV en una lista desde la carpeta "data"
def load_csv_data(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    with open(filepath, 'r') as file:
        reader = csv.reader(file)
        return [row[0] for row in reader]
#Separacion para Direcciones
def load_csv_direccion(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    with open(filepath, 'r') as file:
        return [line.strip() for line in file]

#insertar datos en la tabla persona
def insert_persona_empleado_data(cursor, nombres,apellidos_paterno,apellidos_materno,telefonos,direcciones):
    for i in range(1000):
        nombre = random.choice(nombres) # tomar un nombre de un arreglo de nombres
        apellido_paterno = random.choice(apellidos_paterno) #tomar un apellido de un arreglo de apellidos
        apellido_materno = random.choice(apellidos_materno) #tomar un apellido de un arreglo de apellidos
        telefono = random.choice(telefonos) #escojer un telfono al azar
        direccion = direcciones[i].split(',') #escojer una direccion del areglo direcciones
        #ejecutar el query de inserción de datos
        query = """
        INSERT INTO prueba_bd.empleado (nombre, apellido_paterno, apellido_materno, telefono, direccion, rfc, curp, salario, fecha_contratacion, id_restaurante, id_horario, id_puesto)
        VALUES (%s, %s, %s, %s, ROW(%s, %s, %s, %s),%s, %s, %s,CURRENT_DATE, %s, %s, %s);
        """
         # Valores de ejemplo para RFC, CURP, salario, etc.
        rfc = 'RFC'+str(i)
        curp = 'CURP'
        salario = 1000.00
        id_restaurante = 1
        id_horario = 1
        id_puesto = 1
        #Función execute para ejecutar el query en la base de datos
        cursor.execute(query, (nombre, apellido_paterno, apellido_materno, telefono, direccion[0], direccion[1], direccion[2], direccion[3],
                       rfc, curp, salario, id_restaurante, id_horario, id_puesto))
    
# Programa principal
def main():
    # Cargar los datos de cada archivo CSV
    nombres = load_csv_data('nombres.csv')
    apellidos_paternos = load_csv_data('apellido_paterno.csv')
    apellidos_maternos = load_csv_data('apellido_materno.csv')
    telefonos = load_csv_data('telefono.csv')
    direcciones = load_csv_direccion('direccion.csv')
    
    # Conectar a la base de datos y realizar la inserción
    conn = connect_to_db()
    cursor = conn.cursor()
    
    # Verificar que todos los archivos tengan el mismo número de elementos
    sizes = [len(nombres), len(apellidos_paternos), len(apellidos_maternos), len(telefonos), len(direcciones)]
    if len(set(sizes)) > 1:
        print("Error: Los archivos CSV no tienen el mismo número de filas.")
        return
    
    try:
        opcion = True
        while(opcion):
            limpiar_pantalla()
            inserciones_1000 = int(input("Escribe del 1 al 1000 cuantas inserciones quieres hacer,\n si eliges 1 es equivalente a 1000 registros, si eliges 1000 es equivalente a 1 millon registros\n"))
            for i in range(inserciones_1000):
                insert_persona_empleado_data(cursor,nombres,apellidos_paternos,apellidos_maternos,telefonos,direcciones)
                print(f"Inserción: {i*1000}",end='\r')
            control = input("¿Insertar de nuevo? 's' para volver a insertar más registros más ó 'n' para terminar: ")
            if control.lower() == 's':
                continue
            elif control.lower() == 'n':
                opcion = False
            else:
                print("Opción no valida")
                break
        conn.commit()
        print("Insercion terminada")
    except Exception as e:
        print("Error: ",e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()    