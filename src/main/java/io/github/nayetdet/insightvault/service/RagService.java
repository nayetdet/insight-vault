package io.github.nayetdet.insightvault.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RagService {

    private final ChatClient chatClient;
    private final ElasticsearchVectorStore vectorStore;

    public String query(String question) {
        SearchRequest searchRequest = SearchRequest
                .builder()
                .query(question)
                .topK(5)
                .similarityThreshold(0.7)
                .build();

        String documents = vectorStore
                .doSimilaritySearch(searchRequest)
                .stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        if (documents.isEmpty()) {
            return "Não encontrei nenhum documento que responda a essa pergunta.";
        }

        String prompt = """
            Você é um assistente especializado em analisar documentos. Sua função é responder perguntas
            com base estritamente no conteúdo dos documentos fornecidos.
   
            DIRETRIZES:
            1. Responda de forma clara, concisa e em PORTUGUÊS
            2. Baseie-se EXCLUSIVAMENTE nas informações dos documentos
            3. Se a pergunta for ambígua, peça esclarecimentos
            4. Para cálculos ou situações complexas, explique passo a passo
            5. Mantenha um tom neutro e objetivo
            6. Se não encontrar a informação, responda "Não encontrei nenhum documento que responda a essa pergunta."
            
            FORMATO DE RESPOSTA:
            - Comece com uma resposta direta
            - Em seguida, adicione detalhes relevantes
            - Finalize com a fonte (página/seção quando aplicável)
    
            DOCUMENTOS:
            """ + documents + """
            
            PERGUNTA:
            """ + question + """
            
            (Responda exclusivamente com base nos documentos acima, em português)
            """;

        return chatClient
                .prompt()
                .user(prompt)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }

    public void ingest(MultipartFile file) {
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(file.getResource());
        List<Document> batch = new TokenTextSplitter().apply(pdfReader.read());
        vectorStore.add(batch);
    }

}
