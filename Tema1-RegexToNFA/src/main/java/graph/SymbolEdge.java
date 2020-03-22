package graph;

import org.jgrapht.graph.DefaultEdge;

public class SymbolEdge extends DefaultEdge {
    private String symbol;

    public SymbolEdge() {
    }

    public SymbolEdge(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
