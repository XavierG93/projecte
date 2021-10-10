package gerisoft.apirest.dto;

import org.springframework.data.domain.Page;

import java.util.List;
/**
 *
 * @author Gurrea
 */
/*
Classe amb objectiu de rebre els resultats amb l'informacio ordenada i sense les propietats del pageable
*/
public class PaginateResponse {
    private List results;

    private Paging paging;

    public List getResults() {
        return results;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Page page) {
        this.paging = new Paging(page);
    }

    public PaginateResponse(Page response) {
        //Aqui tenim els resultats
        this.results = response.getContent();
        //Aqui afegim la configuracio de la informacio que cerquem
        this.paging = new Paging(response);
    }
    public PaginateResponse(List result) {
        this.results = result;
    }
    
    
    public static class Paging {
        private long page;//numero de pagines
        private int size;//informacio mostrada a cada pagina
        private int totalPages;//el total de pagines
        private long total;
        
        public long getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public long getTotal() {
            return total;
        }

       
        public Paging(Page<Object> response) {//Object, ha de ser generic per cobrir totes les entitats
            this.page = response.getPageable().getPageNumber();//retorna el numero de pagina
            this.size = response.getPageable().getPageSize();//retorna el tamany de la pagina, la informacio que es pot mostrar
            this.totalPages = response.getTotalPages();//retorna el total de pagines
            this.total = response.getTotalElements();//retorna el complet d'entitats que troba la recerca
        }
    }
}

