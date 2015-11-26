from java.lang import Object
from org.biojava.nbio.structure import StructureImpl
from org.mdconverter.api import ReadStructure


class readstructureimpl(ReadStructure, Object):

    def __init__(self):
        print 'Initializing'
        pass

    def readFileToStructure(self, path):
        struct = StructureImpl()
        struct.setName(path)
        return struct
