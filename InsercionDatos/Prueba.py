from faker import Faker
from faker.providers import BaseProvider

# Asumiendo que tienes tus funciones de generación de RFC y CURP
from GenerarRFC import generar_rfc
from GenerarCurp import generar_curp
from GenerarFechaNac import generar_fecha_aleatoria

class MexicanIDProvider(BaseProvider):
    def rfc(self, nombre, apellido_paterno, apellido_materno, fecha_nacimiento):
        return generar_rfc(nombre, apellido_paterno, apellido_materno, fecha_nacimiento)
    
    def curp(self, nombre, apellido_paterno, apellido_materno, fecha_nacimiento, estado):
        return generar_curp(nombre, apellido_paterno, apellido_materno, fecha_nacimiento, estado)

# Crear una instancia de Faker con el nuevo proveedor
fake = Faker('es_MX')
fake.add_provider(MexicanIDProvider)

# Ejemplo de generación
nombre = fake.first_name()
apellido_paterno = fake.last_name()
apellido_materno = fake.last_name()
fecha_nacimiento = generar_fecha_aleatoria()
estado = "México"

rfc = fake.rfc(nombre, apellido_paterno, apellido_materno, fecha_nacimiento)
curp = fake.curp(nombre, apellido_paterno, apellido_materno, fecha_nacimiento, estado)

print("RFC:", rfc)
print("CURP:", curp)
