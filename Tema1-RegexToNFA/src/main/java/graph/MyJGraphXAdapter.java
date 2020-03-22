package graph;
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import model.NFA;
import model.State;
import model.Transition;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyJGraphXAdapter extends JApplet
{
    private static final Dimension DEFAULT_SIZE = new Dimension(1280, 1240);

    private ListenableGraph<String, SymbolEdge> graph;

    private JGraphXAdapter<String, SymbolEdge> jgxAdapter;

    private NFA nfa;

    public MyJGraphXAdapter() throws HeadlessException {
        super();
    }

    public MyJGraphXAdapter(NFA nfa) throws HeadlessException {
        super();
        this.nfa = nfa;
    }

    @Override
    public void init()
    {
        // add vertexes and edges to the graph
        initializeGraph(nfa);
        // create a visualization using JGraph, via an adapter
       jgxAdapter = new JGraphXAdapter<>(graph);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        mxIGraphLayout layout = new mxCompactTreeLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
    }

    public void initializeGraph(NFA nfa){
        graph = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(SymbolEdge.class));

        List<Transition> transitions = nfa.getTransitionsList();
        List<String> states = nfa.getStates().stream().map(State::getStateId).collect(Collectors.toList());

        for (String state : states){
            graph.addVertex(state);
        }

        for (Transition transition: transitions){
            String from = transition.getFromState().toString();
            String to = transition.getToState().toString();
            String symbol = transition.getTransitionSymbol();
            graph.addEdge(from, to, new SymbolEdge(symbol));
        }
    }


    public ListenableGraph<String, SymbolEdge> getGraph() {
        return graph;
    }

    public void setGraph(ListenableGraph<String, SymbolEdge> graph) {
        this.graph = graph;
    }

    public JGraphXAdapter<String, SymbolEdge> getJgxAdapter() {
        return jgxAdapter;
    }

    public void setJgxAdapter(JGraphXAdapter<String, SymbolEdge> jgxAdapter) {
        this.jgxAdapter = jgxAdapter;
    }
}
