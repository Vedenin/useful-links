package com.github.vedenin.project_parser.containers;

import lombok.Data;

/**
 *  Word - Category mapping
 *
 * Created by Slava Vedenin on 5/14/2016.
 */
@Data(staticConstructor = "create")
public class WordCategoryContainer {
    private String category;
    private Integer number;
    private String word;
}
