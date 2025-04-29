import java.util.*;

class Voto {
    private int id;
    private int votanteId;
    private int candidatoId;
    private String timestamp;

    public Voto(int id, int votanteId, int candidatoId, String timestamp) {
        this.id = id;
        this.votanteId = votanteId;
        this.candidatoId = candidatoId;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getVotanteId() {
        return votanteId;
    }

    public int getCandidatoId() {
        return candidatoId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVotanteId(int votanteId) {
        this.votanteId = votanteId;
    }

    public void setCandidatoId(int candidatoId) {
        this.candidatoId = candidatoId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

class Candidato {
    private int id;
    private String nombre;
    private String partido;
    private Queue<Voto> votosRecibidos;

    public Candidato(int id, String nombre, String partido) {
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.votosRecibidos = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPartido() {
        return partido;
    }

    public Queue<Voto> getVotosRecibidos() {
        return votosRecibidos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public void agregarVoto(Voto voto) {
        votosRecibidos.offer(voto);
    }
}

class Votante {
    private int id;
    private String nombre;
    private boolean yaVoto;

    public Votante(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.yaVoto = false;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public boolean getYaVoto() {
        return yaVoto;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void marcarComoVotado() {
        this.yaVoto = true;
    }}

class UrnaElectoral {
    private LinkedList<Candidato> listaCandidatos;
    private Stack<Voto> historialVotos;
    private Queue<Voto> votosReportados;
    private int idCounter;

    public UrnaElectoral() {
        this.listaCandidatos = new LinkedList<>();
        this.historialVotos = new Stack<>();
        this.votosReportados = new LinkedList<>();
        this.idCounter = 1;
    }

    public boolean verificarVotante(Votante votante) {
        return votante.getYaVoto();
    }
    public boolean registrarVoto(Votante votante, int candidatoId) {
        if (verificarVotante(votante)) {
            System.out.println("El votante ya ha emitido su voto.");
            return false;
        }
        Candidato candidato = null;
        for (Candidato c : listaCandidatos) {
            if (c.getId() == candidatoId) {
                candidato = c;
                break;
            }}
        if (candidato == null) {
            System.out.println("Candidato no encontrado.");
            return false;
        }
        Voto nuevoVoto = new Voto(idCounter++, votante.getId(), candidatoId, "hh:mm:s");
        candidato.agregarVoto(nuevoVoto);
        historialVotos.push(nuevoVoto);
        votante.marcarComoVotado();

        System.out.println("Voto registrado exitosamente.");
        return true;
    }

    public boolean reportarVoto(Candidato candidato, int idVoto) {
        for (Voto voto : votosReportados) {
            if (voto.getId() == idVoto) {
                System.out.println("Este voto ya fue reportado.");
                return false;
            }}
        for (Voto voto : candidato.getVotosRecibidos()) {
            if (voto.getId() == idVoto) {
                votosReportados.add(voto);
                System.out.println("Voto reportado correctamente.");
                return true;
            }}
        System.out.println("Voto no encontrado en el candidato.");
        return false;
    }
    public Map<Integer, Integer> obtenerResultados() {
        Map<Integer, Integer> conteo = new HashMap<>();
        for (Voto voto : historialVotos) {
            conteo.put(voto.getCandidatoId(), conteo.getOrDefault(voto.getCandidatoId(), 0) + 1);
        }
        return conteo;
    }
    public LinkedList<Candidato> getListaCandidatos() {
        return listaCandidatos;
    }
    public Stack<Voto> getHistorialVotos() {
        return historialVotos;
    }
    public Queue<Voto> getVotosReportados() {
        return votosReportados;
    }
}