package spittr.web;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import spittr.entity.Spittle;
import spittr.entity.SpittleForm;
import spittr.service.SpittleManager;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";
    private static final String PAGE_SIZE = "5";

    private SpittleManager spittleManager;

    @Autowired
    public SpittleController(SpittleManager spittleManager) {
        this.spittleManager = spittleManager;
    }


    //simple paging using PagedListHolder
/*  @RequestMapping(method=RequestMethod.GET)
  public List<Spittle> spittles() {
    return spittleManager.findAll();
  }*/

/*  @RequestMapping(method=RequestMethod.GET)
  public String spittles(
          @RequestParam(value="page",
                  defaultValue="1") int page, Model model) {

    PagedListHolder<Spittle> pagedListHolder = new PagedListHolder<>(spittleManager.findAll());
    pagedListHolder.setPageSize(2);

    if(page < 1 || page > pagedListHolder.getPageCount()) page=1;
    pagedListHolder.setPage(page-1);

    model.addAttribute("page", page);
    model.addAttribute("spittleList", pagedListHolder.getPageList());
    return "spittles";
  }*/

    //custom paging, more efficient
    @RequestMapping(method = RequestMethod.GET)
    public String spittles(
            @RequestParam(value = "page",
                    defaultValue = "1") int page, @RequestParam(value = "pageSize",
            defaultValue = PAGE_SIZE) int pageSize, Model model) {

        List<Spittle> list = spittleManager.findByPage(pageSize, page);

        if (list.isEmpty()) {
            list = spittleManager.findByPage(pageSize, 1);
        }

        model.addAttribute("page", page);
        model.addAttribute("spittleList", list);
        return "spittles";
    }


    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
    public String spittle(
            @PathVariable("spittleId") long spittleId,
            Model model) {
        model.addAttribute(spittleManager.findOne(spittleId));
        return "spittle";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSpittle(SpittleForm form, Model model) throws Exception {

        Long id = spittleManager.save(new Spittle(null, form.getMessage(), new Date(),
                form.getLongitude(), form.getLatitude())).getId();

        MultipartFile profilePicture = form.getProfilePicture();
        if (profilePicture!=null) {
            profilePicture.transferTo(new File("/spittles/" + id + ".jpg"));
        }
        return "redirect:/spittles";
    }

}
